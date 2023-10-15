package dev.lsrdev.authbasejwt.infra.email.services;


import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;

public interface EmailService {
    void sendResetPasswordEmail(String url, PasswordResetToken passwordResetToken);
}

