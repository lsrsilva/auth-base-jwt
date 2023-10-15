package dev.lsrdev.authbasejwt.domain.passwordresettoken.services;


import dev.lsrdev.authbasejwt.domain.auth.dto.ChangePasswordDTO;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;

import java.util.UUID;

public interface PasswordResetTokenService {
    PasswordResetToken save(PasswordResetToken passwordResetToken);

    void validateToken(UUID token);

    void changePassword(ChangePasswordDTO changePasswordDTO, UUID uuid);
}

