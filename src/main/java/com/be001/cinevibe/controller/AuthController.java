package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.SignInRequestDTO;
import com.be001.cinevibe.dto.SignInResponseDTO;
import com.be001.cinevibe.service.impl.AuthServiceImpl;
import com.be001.cinevibe.util.BaseResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.registerUser(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<SignInResponseDTO>> signIn(@RequestBody @Valid SignInRequestDTO request) {
        return authService.signInUser(request);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<BaseResponse<String>> signOut(@RequestHeader("Authorization") String authorizationHeader) {
        return authService.signOutUser(authorizationHeader);
    }
}
