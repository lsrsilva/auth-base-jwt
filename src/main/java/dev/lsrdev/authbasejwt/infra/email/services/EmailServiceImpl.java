package dev.lsrdev.authbasejwt.infra.email.services;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;
import dev.lsrdev.authbasejwt.infra.email.model.ResetPassword;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(SpringTemplateEngine thymeleafTemplateEngine, JavaMailSender javaMailSender) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendResetPasswordEmail(String url, PasswordResetToken passwordResetToken) {
        ResetPassword resetPassword = new ResetPassword(
                url.concat("/auth/change-psw/").concat(passwordResetToken.getToken().toString()),
                passwordResetToken.getUser().getEmail()
        );
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("resetPassword", resetPassword);
        String htmlBody = thymeleafTemplateEngine.process("reset-psw.html", thymeleafContext);

        sendHtmlEmail(passwordResetToken.getUser().getEmail(), "Recuperação de senha", htmlBody);
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom("Petcare");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(htmlBody, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new HttpStatusException("Erro ao enviar e-mail!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

