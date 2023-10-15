package dev.lsrdev.authbasejwt.domain.auth.services;

import dev.lsrdev.authbasejwt.domain.auth.dto.AuthDTO;
import dev.lsrdev.authbasejwt.domain.auth.dto.LoginDTO;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    AuthDTO login(LoginDTO loginDTO) throws HttpStatusException, BadCredentialsException;

    String refresh(User userDTO);

    User getCurrentUser();

    AuthDTO refreshAuth();

}
