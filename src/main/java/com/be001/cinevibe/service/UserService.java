package com.be001.cinevibe.service;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    Logger logger = Logger.getLogger(UserService.class.getName());
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
        if (user!=null) {
            userRepository.save(user);
        }
    }
    public void deleteById(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
        }
    }
    public void update(Long id,User updatedUser) {
        if (id==null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User is not found");
        }
        User user=userOptional.get();
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setUsername(updatedUser.getUsername());
        user.setUserRole(updatedUser.getUserRole());
        user.setCreatedAt(updatedUser.getCreatedAt());
        user.setUpdatedAt(updatedUser.getUpdatedAt());
        user.setEnabled(updatedUser.getEnabled());
        userRepository.save(user);
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
