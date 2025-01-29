package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.UserResponseDTO;
import com.be001.cinevibe.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        logger.log(Level.INFO, "Some user try register.");

        UserResponseDTO user = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
