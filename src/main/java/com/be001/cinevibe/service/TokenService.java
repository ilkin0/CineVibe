package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token findByValue(String value) {
        Token token = null;
        try {
            token = tokenRepository.findByValue(value).orElseThrow();
        } catch (Exception e){
            log.error("Cannot find token.");
        }
        return token;
    }

    public Token save(Token token) {
        return tokenRepository.save(token);
    }
}
