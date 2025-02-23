package com.be001.cinevibe.service.impl;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.SignInRequestDTO;
import com.be001.cinevibe.dto.SignInResponseDTO;
import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.enums.UserRole;
import com.be001.cinevibe.service.JwtService;
import com.be001.cinevibe.service.TokenService;
import com.be001.cinevibe.service.UserService;
import com.be001.cinevibe.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Value("${security.jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void registerUser(@Valid RegisterRequestDTO request) {
        String username = request.username();
        checkUsername(username);

        String email = request.email();
        checkEmail(email);

        String password = request.password();
        checkPassword(password);

        User user = User.builder().
                email(email).
                password(passwordEncoder.encode(password)).
                username(username).
                userRole(UserRole.USER).
                build();

        userService.save(user);
    }

    @Override
    public SignInResponseDTO signInUser(SignInRequestDTO request) {
        log.info("User try singing in.");

        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
            var authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (Exception e) {
            log.info("Username or password incorrect.");
            throw new RuntimeException("Username or password incorrect.");
        }
        String accessToken = jwtService.generateAccessToken(request.username());
        String refreshToken = jwtService.generateRefreshToken(request.username());

        Token token = new Token(accessToken,
                Instant.now(),
                Instant.now()
                        .plusSeconds(accessTokenExpiration),
                false,
                false,
                userService.findByUsername(request.username())
        );

        tokenService.save(token);

        return new SignInResponseDTO(accessToken, refreshToken);
    }

    @Override
    public void signOutUser(String authorizationHeader) throws Exception {
        log.info("User is signing out.");

        if (!authorizationHeader.startsWith("Bearer ")) {
            log.error("Invalid Authorization header.");
            throw new Exception();
        }

        String accessToken = authorizationHeader.substring(7);

        if (!jwtService.isValidToken(accessToken, true)) {
            log.error("Invalid token provided for logout.");
            throw new RuntimeException("Invalid token.");
        }

        Token token = tokenService.findByValue(accessToken);
        if (token == null) {
            log.error("Token not found in the database.");
            throw new RuntimeException("Token not found.");
        }

        token.setRevoked(true);
        token.setExpired(true);
        tokenService.save(token);

        log.info("User successfully signed out.");
    }


    private void checkUsername(String username) {
        if (userService.existsByUsername(username)) {
            log.error("Username exist.");
            throw new RuntimeException("Invalid username. Username exist.");
        }
    }

    private void checkEmail(String email) {
        if (userService.existsByEmail(email)) {
            log.error("Email exist.");
            throw new RuntimeException("Invalid email. Email exist.");
        }
    }

    private void checkPassword(String password) {

        boolean isPasswordContainNumber = false,
                isPasswordContainLowerCase = false,
                isPasswordContainUpperCase = false;


        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!isPasswordContainNumber && c > 47 && c < 58) {
                isPasswordContainNumber = true;
            }
            if (!isPasswordContainLowerCase && c > 96 && c < 123) {
                isPasswordContainLowerCase = true;
            }
            if (!isPasswordContainUpperCase && c > 64 && c < 91) {
                isPasswordContainUpperCase = true;
            }
        }
        if (!isPasswordContainUpperCase) {
            log.error("Password doesn't contain uppercase character.");
            throw new RuntimeException("Invalid password. Password doesn't contain uppercase character.");
        }
        if (!isPasswordContainLowerCase) {
            log.error("Password doesn't contain lowercase character.");
            throw new RuntimeException("Invalid password. Password doesn't contain lowercase character.");
        }
        if (!isPasswordContainNumber) {
            log.error("Password doesn't contain number.");
            throw new RuntimeException("Invalid password. Password doesn't contain number.");
        }
    }

}
