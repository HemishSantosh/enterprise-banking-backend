package com.bank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET =
            "EnterpriseBankingSystemSecretKeyForJwtAuthentication2026Hemish123456";

    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Generate JWT Token
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(key)
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic Claim Extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // Extract All Claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validate Token
    public boolean isTokenValid(String token, String email) {

        return extractUsername(token).equals(email)
                && !isTokenExpired(token);
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }
}