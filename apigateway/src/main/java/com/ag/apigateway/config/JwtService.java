package com.ag.apigateway.config;

import java.util.HexFormat;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
     private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public boolean isValid(String token) {

        try {
            
            Claims claims = Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();
            String role = claims.get("role", String.class);

            return subject != null
                    && !subject.isBlank()
                    && role != null
                    && !role.isBlank();

        } catch (JwtException | IllegalArgumentException exception) {

            return false;
        }
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                HexFormat.of().parseHex(
                        jwtProperties.getSecretKey());

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
