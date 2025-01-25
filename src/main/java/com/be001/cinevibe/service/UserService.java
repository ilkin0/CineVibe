package com.be001.cinevibe.service;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
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

    public boolean existsByUsername(String username) {
        boolean b = userRepository.existsByUsername(username);
        if (!b) logger.severe("User with username :" + username + " doesn't exist.");
        return b;
    }
    public boolean existsByEmail(String email) {
        boolean b = userRepository.existsByUsername(email);
        if (!b) logger.severe("User with email :" + email + " doesn't exist.");
        return b;
    }

    public User save(User user){
        logger.log(Level.FINE,"User saved.");
        return userRepository.save(user);
    }
}
