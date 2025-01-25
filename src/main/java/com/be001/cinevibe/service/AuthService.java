package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.RegisterRequest;
import com.be001.cinevibe.dto.RegisterResponse;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.enums.UserRole;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(AuthService.class.getName());

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(@Valid RegisterRequest request) {
        String username = request.username();
        checkUsername(username);

        String email = request.email();
        checkEmail(email);

        String password = request.password();
        checkPassword(password);

        User user = new User(email,
                passwordEncoder.encode(password),
                username,
                UserRole.USER,
                true,
                true,
                true,
                true);

        return new RegisterResponse(userService.save(user).getUsername());
    }

    private void checkUsername(String username){
        if (userService.existsByUsername(username)){
            logger.severe("Username exist.");
            throw new RuntimeException("Invalid username. Username exist.");
        }
    }

    private void checkEmail(String email){
        if (userService.existsByEmail(email)){
            logger.severe("Email exist.");
            throw new RuntimeException("Invalid email. Email exist.");
        }

        if (!email.contains("@")) {
            logger.severe("Email must be contain @.");
            throw new RuntimeException("Invalid email. Email must be contain @.");
        }
    }

    private void checkPassword(String password) {
        if (password.length() < 8) {
            logger.severe("Password length cannot be less than 8.");
            throw new RuntimeException("Invalid password. Password length cannot be less than 8.");
        }

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
            logger.severe("Password doesn't contain uppercase character.");
            throw new RuntimeException("Invalid password. Password doesn't contain uppercase character.");
        }
        if (!isPasswordContainLowerCase) {
            logger.severe("Password doesn't contain lowercase character.");
            throw new RuntimeException("Invalid password. Password doesn't contain lowercase character.");
        }
        if (!isPasswordContainNumber) {
            logger.severe("Password doesn't contain number.");
            throw new RuntimeException("Invalid password. Password doesn't contain number.");
        }
    }

}
