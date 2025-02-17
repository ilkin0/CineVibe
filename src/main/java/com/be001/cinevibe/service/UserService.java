package com.be001.cinevibe.service;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getList() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void save(User user) {
        if (user != null) {
            userRepository.save(user);
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
        }
    }

    public void update(Long id, User updatedUser) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        User user = userRepository.findById(id).orElseThrow();
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            user.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getUserRole() != null) {
            user.setUserRole(updatedUser.getUserRole());
        }
        if (updatedUser.getUpdatedAt() != null) {
            user.setUpdatedAt(LocalDateTime.now());
        }
        userRepository.save(user);
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

}
