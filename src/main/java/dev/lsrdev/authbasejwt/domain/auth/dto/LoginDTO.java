package dev.lsrdev.authbasejwt.domain.auth.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    private String login;

    private String password;

    public LoginDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken createAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }

}
