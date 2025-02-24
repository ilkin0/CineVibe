package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.exception.NoDataFoundException;
import com.be001.cinevibe.mapper.ProfileMapper;
import com.be001.cinevibe.model.CustomUserDetails;
import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final ProfileMapper mapper;

    public UserService(UserRepository userRepository, TokenService tokenService, ProfileMapper mapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
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
                            log.info("User with username {} not found", username);
                            return new UsernameNotFoundException("User not found");
                        }
                );
    }

    public UserProfileDTO getProfile() throws NoDataFoundException {
        return mapper.toProfile(getPrincipal());
    }
    public void deactivateAccount(Long id) throws NoDataFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoDataFoundException("No user found by given id"));
        user.setEnabled(false);
        userRepository.save(user);
        log.warn("Account is disabled: {}", user.getEmail());
    }

    public void activateAccount(Long id) throws NoDataFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoDataFoundException("No user found by given id"));
        user.setEnabled(true);
        userRepository.save(user);
        log.warn("Account is enabled: {}", user.getEmail());
    }

    public List<UserProfileDTO> findAllProfiles(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(mapper::toProfile).toList();
    }

    public void deleteById(Long id) {
        tokenService.deleteByUserId(id);
        userRepository.deleteById(id);
        log.warn("Account is deleted by id{}", id);
    }

    public UserProfileDTO addFollowers(Long followingId) throws NoDataFoundException {
        log.info("You try add some follow.");

        User follower = getPrincipal();
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following not found"));

        if (Objects.equals(follower.getId(), followingId))
            throw new IllegalArgumentException("You can not follow yourself!");

        follower.getFollows().add(following);
        return mapper.toProfile(userRepository.save(follower));
    }

    public void removeFollowers(Long followingId) throws NoDataFoundException {
        log.info("You try remove some follow.");

        User follower = getPrincipal();

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following not found"));

        follower.getFollows().remove(following);

        userRepository.deleteFollowRelation(follower.getId(), followingId);
        userRepository.save(follower);
    }

    public ResponseEntity<UserProfileDTO> updateProfile(UserProfileDTO profileInfo, HttpServletRequest request) throws NoDataFoundException {
        ResponseEntity<UserProfileDTO> user = ResponseEntity.ok(mapper.
                toProfile(userRepository.save(mapper.toEntity(getPrincipal(), profileInfo))));

        String token = request.getHeader("Authorization").substring(7);
        Token byValue = tokenService.findByValue(token);
        byValue.setRevoked(true);
        byValue.setExpired(true);
        tokenService.save(byValue);

        return user;
    }

    public void save(User user) {
        log.info("User saved.");
        userRepository.save(user);
    }

    public User getPrincipal() throws NoDataFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = Optional.empty();
        if (principal instanceof CustomUserDetails details) {
            user = Optional.of(details.user());
        }
        if (user.isEmpty()) {
            throw new NoDataFoundException("No principal found!");
        }
        return user.get();
    }
}
