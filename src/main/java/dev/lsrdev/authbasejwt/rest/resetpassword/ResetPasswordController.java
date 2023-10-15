package dev.lsrdev.authbasejwt.rest.resetpassword;

import dev.lsrdev.authbasejwt.domain.auth.dto.ChangePasswordDTO;
import dev.lsrdev.authbasejwt.domain.passwordresettoken.services.PasswordResetTokenService;
import dev.lsrdev.authbasejwt.domain.user.services.UserService;
import dev.lsrdev.authbasejwt.rest.JsonResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/reset-psw")
public class ResetPasswordController {

    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;

    public ResetPasswordController(
            PasswordResetTokenService passwordResetTokenService, UserService userService
    ) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<JsonResponseDTO> resetPassword(HttpServletRequest request, @RequestBody String email) {
        this.userService.sendResetPasswordEmail(request.getHeader("ORIGIN"), email);
        return new ResponseEntity<>(
                JsonResponseDTO.ok("Email enviado com sucesso"),
                HttpStatus.OK
        );
    }

    @PostMapping("/change-psw/{uuid}")
    public ResponseEntity<JsonResponseDTO> changePassword(
            @RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable UUID uuid
    ) {
        this.passwordResetTokenService.changePassword(changePasswordDTO, uuid);
        return new ResponseEntity<>(JsonResponseDTO.ok(
                "Password changed!"
        ), HttpStatus.OK);
    }

}
