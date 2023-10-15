package dev.lsrdev.authbasejwt.domain.user.services;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.domain.auth.dto.ChangePasswordDTO;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.services.PasswordResetTokenService;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.domain.user.repositories.UserRepository;
import dev.lsrdev.authbasejwt.infra.email.services.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, PasswordResetTokenService passwordResetTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new HttpStatusException("User not found with id" + id, HttpStatus.NO_CONTENT);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        Assert.notNull(changePasswordDTO, "Data for change password not present!");
        Assert.hasText(changePasswordDTO.getActualPassword(), "Actual password not present!");
        Assert.hasText(changePasswordDTO.getNewPassword(), "User not present!");
        Assert.hasText(changePasswordDTO.getConfirmPassword(), "User not present!");

        if (!passwordEncoder.matches(changePasswordDTO.getActualPassword(), getCurrentUser().getPassword())) {
            throw new HttpStatusException("Actual password is invalid!", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!changePasswordDTO.getConfirmPassword().equals(changePasswordDTO.getNewPassword())) {
            throw new HttpStatusException("Passwords does not match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        userRepository.changePassword(getEncryptedPassword(changePasswordDTO.getNewPassword()), getCurrentUser().getId());

    }

    @Override
    public void sendResetPasswordEmail(String url, String email) {
        User user = findByEmail(email).orElseThrow(() -> new HttpStatusException("No user found with this e-mail!", HttpStatus.UNPROCESSABLE_ENTITY));
        PasswordResetToken passwordResetToken = passwordResetTokenService.save(new PasswordResetToken(user));
        emailService.sendResetPasswordEmail(url, passwordResetToken);
    }

    private String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
