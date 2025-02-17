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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final TokenService tokenService;

    private final ProfileMapper mapper;

    public UserService(UserRepository userRepository, TokenService tokenService, @Qualifier("profileMapperImpl") ProfileMapper mapper) {
        this.repository = userRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public User findByUsername(String username) {
        return repository
                .findByUsername(username).orElseThrow(() -> {
                            log.info("User with username " + username + " not found");
                            return new UsernameNotFoundException("User not found");
                        }
                );
    }

    public UserProfileDTO getProfile() throws NoDataFound {
        Optional<User> currentPrincipal = getPrincipal();
        if (currentPrincipal.isPresent()) return mapper.toProfile(currentPrincipal.get());
        throw new NoDataFound("No principal found!");
    }

    public void deactivateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(false);
        repository.save(user);
        log.warn("Account is disabled: " + user.getEmail());
    }

    public void activateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(true);
        repository.save(user);
        log.warn("Account is enabled: " + user.getEmail());
    }

    public List<UserProfileDTO> findAllProfiles(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toProfile).toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        log.warn("Account is deleted by id" + id);
    }

    public UserProfileDTO addFollowers(Long followingId) throws NoDataFound { // 14 -- 14
        if (getPrincipal().isPresent()) { //
            User follower = getPrincipal().get(); // follower -- 14 olan
            User following = repository.findById(followingId) // 11 olan
                    .orElseThrow(() -> new RuntimeException("Following not found"));

            if(Objects.equals(follower.getId(), followingId)) throw new IllegalArgumentException("You can not follow yourself!");

            follower.getFollows().add(following);
            return mapper.toProfile(repository.save(follower));
        }
        throw new NoDataFound("No principal found!");
    }

    @Transactional
    public UserProfileDTO removeFollowers(Long followingId) throws NoDataFound {

        if (getPrincipal().isPresent()) {
            User follower = getPrincipal().get();

            User following = repository.findById(followingId)
                    .orElseThrow(() -> new RuntimeException("Following not found"));


            follower.getFollows().forEach(System.out::println); // TODO LAZY INITIALIZATION PROBLEM

            follower.getFollows().remove(following); //  SET, ind

            return mapper.toProfile(repository.save(follower));
        }
        throw new NoDataFound("No principal found!");
    }

    public ResponseEntity<UserProfileDTO> updateProfile(UserProfileDTO profileInfo, HttpServletRequest request) {
        if (getPrincipal().isPresent()) {

            String newEmail = profileInfo.getEmail();
            String newUsername = profileInfo.getUsername();

            UserProfileDTO userProfileDTO = new UserProfileDTO(newEmail, newUsername);
            ResponseEntity<UserProfileDTO> user = ResponseEntity.ok(mapper.toProfile(repository.save(mapper.toEntity(getPrincipal().get(), userProfileDTO))));

            String token = request.getHeader("Authorization").substring(7);
            Token byValue = tokenService.findByValue(token);
            byValue.setRevoked(true);
            byValue.setExpired(true);
            tokenService.save(byValue);

            return user;

        }
        return ResponseEntity.notFound().build();
    }

    public void save(User user) {
        log.info("User saved.");
        repository.save(user);
    }

    public Optional<User> getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails details) {
            return Optional.of(details.getUser());
        }
        System.out.println(principal);

        return Optional.empty();
    }
}
