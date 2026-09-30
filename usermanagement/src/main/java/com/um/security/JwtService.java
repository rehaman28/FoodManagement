package com.um.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service 
@ConfigurationProperties(prefix = "security.jwt")
public class JwtService {

    private String secretKey;

    private long expirationTime;

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return generateToken(extraClaims, userDetails.getUsername());
    }

    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, expirationTime);
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            String username,
            long expiration) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = HexFormat.of().parseHex(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
