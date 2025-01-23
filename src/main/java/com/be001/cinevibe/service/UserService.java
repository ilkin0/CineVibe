package com.be001.cinevibe.service;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username).orElseThrow(() -> {
                    logger.severe("User with username " + username + " not found");
                    return new UsernameNotFoundException("User not found");
                }
                );
    }
}
