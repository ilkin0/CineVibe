package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService{
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token findByValue(String value){
        return tokenRepository.findByValue(value).orElseThrow();
    }
}
