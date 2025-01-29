package com.be001.cinevibe.service.impl;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.UserResponseDTO;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.enums.UserRole;
import com.be001.cinevibe.repository.UserRepository;
import com.be001.cinevibe.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already in use.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(request.getEmail(), request.getPassword(), request.getUsername(),
                UserRole.USER, LocalDateTime.now(), LocalDateTime.now(), true);

        userRepository.save(newUser);

        return mapToUserResponseDTO(newUser);
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .message("SUCCESS")
                .build();
    }
}
