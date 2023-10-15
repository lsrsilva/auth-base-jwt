package dev.lsrdev.authbasejwt.infra.security.jwt.provider;

import dev.lsrdev.authbasejwt.domain.auth.services.AuthService;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import dev.lsrdev.authbasejwt.infra.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {
    private final SecretKey key;
    private final AuthService authService;
    @Value("${jwt.expiration}")
    private String expDate;

    public JwtTokenProviderImpl(@Lazy AuthService authService, KeyGenerator keyGenerator) {
        this.authService = authService;
        key = keyGenerator.generateKey();
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims()
                .subject(user.getUsername())
                .issuer("auth-jwt")
                .issuedAt(new Date())
                .expiration(toDate(LocalDateTime.now().plusMinutes(Long.parseLong(expDate))))
                .build();
        return Jwts.builder()
                .signWith(key)
                .header().add("typ", "JWT").and()
                .claims(claims)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.authService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return claims.getPayload().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            req.getPathInfo();
        }
        return null;
    }

    public boolean validateToken(String token) throws InvalidJwtTokenException, JwtException {
        return validateToken(token, true);
    }

    public boolean validateToken(String token, boolean verifyInBlacklist) throws InvalidJwtTokenException, JwtException {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch ( IllegalArgumentException e) {
            throw new InvalidJwtTokenException("Token expirada!");
        }
    }

    private static Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
