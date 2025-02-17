package com.be001.cinevibe.service;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username).orElseThrow(() -> {
                            log.info("User with username " + username + " not found");
                            return new UsernameNotFoundException("User not found");
                        }
                );
    }

    public boolean existsByUsername(String username) {
        boolean b = userRepository.existsByUsername(username);
        if (!b) log.info("User with username :" + username + " doesn't exist.");
        return b;
    }
    public boolean existsByEmail(String email) {
        boolean b = userRepository.existsByUsername(email);
        if (!b) log.info("User with email :" + email + " doesn't exist.");
        return b;
    }

    public User save(User user){
        log.info("User saved.");
        return userRepository.save(user);
    }
}
