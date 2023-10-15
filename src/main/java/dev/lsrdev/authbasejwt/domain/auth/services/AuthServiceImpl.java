package dev.lsrdev.authbasejwt.domain.auth.services;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.domain.auth.dto.AuthDTO;
import dev.lsrdev.authbasejwt.domain.auth.dto.LoginDTO;
import dev.lsrdev.authbasejwt.domain.permission.adapters.PermissionAdapter;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.domain.user.services.UserService;
import dev.lsrdev.authbasejwt.infra.security.jwt.provider.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PermissionAdapter permissionAdapter;

    public AuthServiceImpl(UserService userService, @Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PermissionAdapter permissionAdapter) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.permissionAdapter = permissionAdapter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEmail = userService.findByEmail(username);
        if (userEmail.isPresent()) {
            return userEmail.get();
        }
        throw new UsernameNotFoundException("Acesso inválido!");
    }

    @Override
    public AuthDTO login(LoginDTO loginDTO) throws HttpStatusException, BadCredentialsException {
        try {
            Optional<User> userOptional = userService.findByEmail(loginDTO.getLogin());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                authenticationManager.authenticate(loginDTO.createAuthenticationToken());
                return new AuthDTO(user.getId(), refresh(user), permissionAdapter.toDtosSet(user.getAuthorities()), user.getEmail());
            }
        } catch (HttpStatusException hse) {
            throw hse;
        } catch (BadCredentialsException bce) {
            throw new HttpStatusException("Usuário ou senha inválidos!", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new HttpStatusException("Erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new HttpStatusException("Usuário ou senha inválidos.", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public String refresh(User userDTO) {
        if (userDTO != null) {
            return jwtTokenProvider.createToken(userDTO);
        }
        throw new HttpStatusException("Usuário ou senha inválidos!", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Boolean hasRolesWith(String role) throws HttpStatusException {
        User user = getCurrentUser();
        boolean canAccess = false;
        if (user != null) {
            Set<Permission> permissions = user.getAuthorities();
            for (Permission permission : permissions) {
                if (permission.getName().contains(role)) {
                    canAccess = true;
                    break;
                }
            }
        }

        if (!canAccess) {
            throw new HttpStatusException("User without permisison!", HttpStatus.UNAUTHORIZED);
        }

        return canAccess;
    }

    @Override
    public AuthDTO refreshAuth() {
        User user = getCurrentUser();
        return new AuthDTO(user.getId(), refresh(user), permissionAdapter.toDtosSet(user.getAuthorities()), user.getEmail());
    }

}
