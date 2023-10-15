package dev.lsrdev.authbasejwt.infra.security.jwt.provider;

import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.infra.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {

    String createToken(User user);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token) throws InvalidJwtTokenException, JwtException;

    boolean validateToken(String token, boolean verifyInBlacklist) throws InvalidJwtTokenException, JwtException;

}
