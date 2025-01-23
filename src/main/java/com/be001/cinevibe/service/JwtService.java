package com.be001.cinevibe.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    public String generateToken(String email, String secret, long expirationMS) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(currentTime))
                .expiration(new Date(currentTime + expirationMS))
                .signWith(getSecretKey(secret))
                .compact();
    }

    private SecretKey getSecretKey(String secret) {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }
}
