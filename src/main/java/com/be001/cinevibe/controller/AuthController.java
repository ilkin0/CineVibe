package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.RegisterRequest;
import com.be001.cinevibe.dto.RegisterResponse;
import com.be001.cinevibe.dto.SignInRequest;
import com.be001.cinevibe.dto.SignInResponse;
import com.be001.cinevibe.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        logger.log(Level.INFO, "Some user try register.");

        var registerResponse = authService.registerUser(request);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request) {
        logger.log(Level.INFO, "Some user try signIn.");

        var signInResponse = authService.signInUser(request);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authorizationHeader) {
        logger.log(Level.INFO, "Some user is trying to sign out.");

        if (!authorizationHeader.startsWith("Bearer ")) {
            logger.severe("Invalid Authorization header.");
            return new ResponseEntity<>("Invalid Authorization header.", HttpStatus.BAD_REQUEST);
        }

        String accessToken = authorizationHeader.substring(7);
        try {
            authService.signOutUser(accessToken);
            return new ResponseEntity<>("Successfully signed out.", HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.severe("Sign out failed: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
