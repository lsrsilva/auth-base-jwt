package dev.lsrdev.authbasejwt.rest.auth;

import dev.lsrdev.authbasejwt.domain.auth.dto.LoginDTO;
import dev.lsrdev.authbasejwt.domain.auth.services.AuthService;
import dev.lsrdev.authbasejwt.rest.JsonResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResponseDTO> login(@RequestBody LoginDTO login) {
        return ResponseEntity.ok(JsonResponseDTO.ok(this.authService.login(login)));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<JsonResponseDTO> validateToken() {
        return new ResponseEntity<>(JsonResponseDTO.ok(
                authService.refreshAuth()
        ), HttpStatus.OK);
    }


}
