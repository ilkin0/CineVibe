package com.be001.cinevibe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.accessToken.secret}")
    private String accessTokenSecret;

    @Value("${security.jwt.refreshToken.secret}")
    private String refreshTokenSecret;

    @Value("${security.jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refreshToken.expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenSecret, accessTokenExpiration);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshTokenSecret, refreshTokenExpiration);
    }

    public String generateToken(String username, String secret, long expirationMS) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTime))
                .expiration(new Date(currentTime + expirationMS))
                .signWith(getSecretKey(secret))
                .compact();
    }

    private SecretKey getSecretKey(String secret) {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isValidToken(String token, boolean isAccessToken) {
        try {
            Claims claims = extractClaims(token, isAccessToken);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token, boolean isAccessToken) {
        Claims claims = extractClaims(token, isAccessToken);
        return claims.getSubject();
    }

    private Claims extractClaims(String token, boolean isAccessToken) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSecretKey(isAccessToken ? accessTokenSecret : refreshTokenSecret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new IllegalArgumentException("Token is not valid or expired: " + e.getMessage());
        }
    }
}
