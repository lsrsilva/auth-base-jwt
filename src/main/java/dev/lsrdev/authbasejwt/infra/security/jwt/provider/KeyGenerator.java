package dev.lsrdev.authbasejwt.infra.security.jwt.provider;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
class KeyGenerator {
    @Value("${jwt.secret}")
    private String keyString;

    public SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.keyString);
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, Keys.hmacShaKeyFor(keyBytes).getAlgorithm());
    }

}
