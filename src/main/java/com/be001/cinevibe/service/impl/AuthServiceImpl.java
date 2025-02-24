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
import com.be001.cinevibe.util.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<BaseResponse<String>> registerUser(RegisterRequestDTO request) {
        try {
            String username = request.username();
            String email = request.email();
            String password = request.password();

            User user = User.builder().
                    email(email).
                    password(passwordEncoder.encode(password)).
                    username(username).
                    userRole(UserRole.USER).
                    isEnabled(true).
                    isAccountNonExpired(true).
                    isAccountNonLocked(true).
                    isCredentialsNonExpired(true).
                    build();

            userService.save(user);
            return new ResponseEntity<>(BaseResponse.message("Successful registration."), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(BaseResponse.fail("Username or email already exists. "), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public <T> ResponseEntity<BaseResponse<T>> signInUser(SignInRequestDTO request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (authenticate == null) {
                log.info("Username or password incorrect.");
                throw new BadCredentialsException("Username or password is incorrect.");
            }

            var user = userService.findByUsername(request.username());
            if (user == null) {
                log.info("User not found: {}", request.username());
                throw new UsernameNotFoundException("User not found.");
            }

            String accessToken = jwtService.generateAccessToken(request.username());
            String refreshToken = jwtService.generateRefreshToken(request.username());

            Token token = new Token(
                    accessToken,
                    Instant.now(),
                    Instant.now().plusSeconds(accessTokenExpiration),
                    false,
                    false,
                    user
            );

            tokenService.save(token);

            SignInResponseDTO responseDTO = new SignInResponseDTO(accessToken, refreshToken);
            BaseResponse<T> response = new BaseResponse<>("Sign-in successful. ", true, (T) responseDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            log.warn("Authentication failed: {}", e.getMessage());
            return new ResponseEntity<>(
                    new BaseResponse<>("Invalid username or password. ", false, null),
                    HttpStatus.UNAUTHORIZED
            );

        } catch (LockedException e) {
            log.warn("User account is locked: {}", e.getMessage());
            return new ResponseEntity<>(
                    new BaseResponse<>("Your account is locked. Please contact support.", false, null),
                    HttpStatus.LOCKED
            );

        } catch (DisabledException e) {
            log.warn("User account is disabled: {}", e.getMessage());
            return new ResponseEntity<>(
                    new BaseResponse<>("Your account is disabled. Please contact support.", false, null),
                    HttpStatus.FORBIDDEN
            );
        } catch (UsernameNotFoundException e) {
            log.warn("User not found: {}", e.getMessage());
            return new ResponseEntity<>(
                    new BaseResponse<>("User not found. ", false, null),
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {
            log.error("Unexpected error during sign-in: {}", e.getMessage());
            return new ResponseEntity<>(
                    new BaseResponse<>("An unexpected error occurred. ", false, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseEntity<BaseResponse<String>> signOutUser(String authorizationHeader) {
        String accessToken = authorizationHeader.substring(7);

        Token token = tokenService.findByValue(accessToken);

        if (token != null) {
            token.setRevoked(true);
            token.setExpired(true);
            tokenService.save(token);
            return new ResponseEntity<>(BaseResponse.message("User successfully signed out. "), HttpStatus.OK);
        }

        return new ResponseEntity<>(BaseResponse.fail("Error happened while signing out. "), HttpStatus.BAD_REQUEST);
    }
}