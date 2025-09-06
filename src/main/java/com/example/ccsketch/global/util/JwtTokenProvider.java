package com.example.ccsketch.global.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenProvider.class.getName());
    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMillis
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    public String createToken(String subjectEmail) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subjectEmail)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException e) {
            LOGGER.log(Level.WARNING, "Invalid JWT signature", e);
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            LOGGER.log(Level.WARNING, "Invalid JWT token", e);
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            LOGGER.log(Level.WARNING, "Expired JWT token", e);
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            LOGGER.log(Level.WARNING, "Unsupported JWT token", e);
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "JWT claims string is empty", e);
            throw new RuntimeException("JWT claims string is empty");
        }
    }
}