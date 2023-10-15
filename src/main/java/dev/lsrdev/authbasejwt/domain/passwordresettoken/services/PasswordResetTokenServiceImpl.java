package dev.lsrdev.authbasejwt.domain.passwordresettoken.services;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.domain.auth.dto.ChangePasswordDTO;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.repositories.PasswordResetTokenRepository;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.domain.user.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordResetTokenServiceImpl(
            PasswordResetTokenRepository passwordResetTokenRepository,
            UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        Assert.notNull(passwordResetToken.getUser(), "User not present!");
        invalidateAllTokensBeforeSave(passwordResetToken.getUser());
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    private void invalidateAllTokensBeforeSave(User user) {
        passwordResetTokenRepository.invalidateAllTokensBeforeSave(user.getId());
    }

    @Override
    public void validateToken(UUID uuid) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(uuid)
                .orElseThrow(() -> new HttpStatusException("Invalid token", HttpStatus.UNPROCESSABLE_ENTITY));
        final LocalDateTime localDateTime = LocalDateTime.now();
        if (passwordResetToken.getExpiryDate().isBefore(localDateTime) || (passwordResetToken.getValid() != null && !passwordResetToken.getValid())) {
            throw new HttpStatusException("Invalid or expired link!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, UUID uuid) {
        Assert.notNull(uuid, "Token must not be null!");
        Assert.notNull(changePasswordDTO.getConfirmPassword(), "Confirm password must not be null!");
        Assert.notNull(changePasswordDTO.getNewPassword(), "New password must not be null!");
        validateToken(uuid);
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new HttpStatusException("Passwords does not match", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(uuid).orElseThrow(
                () -> new HttpStatusException("Invalid token!", HttpStatus.UNPROCESSABLE_ENTITY)
        );
        final LocalDateTime now = LocalDateTime.now();
        if (passwordResetToken.getExpiryDate().isBefore(now)) {
            throw new HttpStatusException("Expired link!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userRepository.changePassword(
                passwordEncoder.encode(changePasswordDTO.getNewPassword()), passwordResetToken.getUser().getId()
        );
        passwordResetTokenRepository.updateValid(false);
    }
}
