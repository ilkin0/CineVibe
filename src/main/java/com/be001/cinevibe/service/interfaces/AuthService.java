package com.be001.cinevibe.service.interfaces;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.SignInRequestDTO;
import com.be001.cinevibe.dto.SignInResponseDTO;

public interface AuthService {
    void registerUser(RegisterRequestDTO request);

    SignInResponseDTO signInUser(SignInRequestDTO request);

    void signOutUser(String authorizationHeader) throws Exception;
}
