package com.be001.cinevibe.service.interfaces;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.SignInRequestDTO;
import com.be001.cinevibe.util.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<BaseResponse<String>> registerUser(RegisterRequestDTO request);

    <T> ResponseEntity<BaseResponse<T>> signInUser(SignInRequestDTO request);

    ResponseEntity<BaseResponse<String>> signOutUser(String authorizationHeader) throws Exception;
}
