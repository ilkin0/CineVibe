package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.exceptions.NoDataFound;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    private final TokenService tokenService;

    private final ProfileMapper mapper;

    public UserService(UserRepository userRepository, TokenService tokenService, ProfileMapper mapper) {
        this.repository = userRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public User findByUsername(String username) {
        return repository
                .findByUsername(username).orElseThrow(() -> {
                    log.info("User with username {} not found", username);
                            return new UsernameNotFoundException("User not found");
                        }
                );
    }

    public UserProfileDTO getProfile() throws NoDataFound {
        return mapper.toProfile(getPrincipal());
    }

    public void deactivateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(false);
        repository.save(user);
        log.warn("Account is disabled: {}", user.getEmail());
    }

    public void activateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(true);
        repository.save(user);
        log.warn("Account is enabled: {}", user.getEmail());
    }

    public List<UserProfileDTO> findAllProfiles(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toProfile).toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        log.warn("Account is deleted by id{}", id);
    }

    public UserProfileDTO addFollowers(Long followingId) throws NoDataFound {
        User follower = getPrincipal();
        User following = repository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following not found"));

        if (Objects.equals(follower.getId(), followingId))
            throw new IllegalArgumentException("You can not follow yourself!");

        follower.getFollows().add(following);
        return mapper.toProfile(repository.save(follower));
    }

    public UserProfileDTO removeFollowers(Long followingId) throws NoDataFound {
        User follower = getPrincipal();

        User following = repository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following not found"));

        follower.getFollows().remove(following);

        return mapper.toProfile(repository.save(follower));
    }

    public ResponseEntity<UserProfileDTO> updateProfile(UserProfileDTO profileInfo, HttpServletRequest request) throws NoDataFound {

        String newEmail = profileInfo.getEmail();
        String newUsername = profileInfo.getUsername();

        UserProfileDTO userProfileDTO = new UserProfileDTO(newEmail, newUsername);
        ResponseEntity<UserProfileDTO> user = ResponseEntity.ok(mapper.toProfile(repository.save(mapper.toEntity(getPrincipal(), userProfileDTO))));

        String token = request.getHeader("Authorization").substring(7);
        Token byValue = tokenService.findByValue(token);
        byValue.setRevoked(true);
        byValue.setExpired(true);
        tokenService.save(byValue);

        return user;
    }

    public void save(User user) {
        log.info("User saved.");
        repository.save(user);
    }

    public User getPrincipal() throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = Optional.empty();
        if (principal instanceof CustomUserDetails details) {
            user = Optional.of(details.getUser());
        }
        if (user.isEmpty()) {
            throw new NoDataFound("No principal found!");
        }
        return user.get();
    }
}
