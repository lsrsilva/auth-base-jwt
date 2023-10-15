package dev.lsrdev.authbasejwt.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordDTO {
    private UUID idUser;
    private String actualPassword;
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordDTO(UUID idUser, String actualPassword, String newPassword, String confirmPassword) {
        this.idUser = idUser;
        this.actualPassword = actualPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
