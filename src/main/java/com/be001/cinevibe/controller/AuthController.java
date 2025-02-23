package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.SignInRequestDTO;
import com.be001.cinevibe.dto.SignInResponseDTO;
import com.be001.cinevibe.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO request) {
        log.info("Some user try register.");
        authService.registerUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO request) {
        log.info("Some user try signIn.");

        var signInResponse = authService.signInUser(request);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("Some user is trying to sign out.");

        try {
            authService.signOutUser(authorizationHeader);
            return new ResponseEntity<>("Successfully signed out.", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Sign out failed: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid Authorization header.", HttpStatus.BAD_REQUEST);
        }
    }
}
