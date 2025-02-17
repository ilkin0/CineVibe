package com.be001.cinevibe.service.interfaces;

import com.be001.cinevibe.dto.RegisterRequest;
import com.be001.cinevibe.dto.SignInRequest;
import com.be001.cinevibe.dto.SignInResponse;
import com.be001.cinevibe.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO registerUser(RegisterRequest request);

    SignInResponse signInUser(SignInRequest request);

    void signOutUser(String authorizationHeader) throws Exception;
}
