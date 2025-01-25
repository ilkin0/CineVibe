package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.UserProfile;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService service;

    /**
     * View the current user's profile.
     */
    @GetMapping("/mine")
    @ResponseStatus(HttpStatus.OK)
    public UserProfile viewProfile() throws NoDataFound {
        return service.getProfile();
    }

    /**
     * View all profiles by user
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserProfile> viewAllProfiles(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.findAllProfiles(pageable);
    }

    /**
     * Add the current user's profile's picture or change existing one.
     */
    @PostMapping("/picture")
    @ResponseStatus(HttpStatus.OK)
    public UserProfile updateProfilePicture(@RequestParam("file") MultipartFile file) throws NoDataFound, IOException {
        return service.updateProfilePicture(file);
    }

    /**
     * Update the username of the user. It gets user from principal and set a new username
     */
    @PutMapping("/username")
    @ResponseStatus(HttpStatus.OK)
    public UserProfile updateUsername(@RequestBody @NotBlank String username) throws NoDataFound {
        return service.updateUsername(username);
    }

    /**
     * Update the email of the user. It takes user from principal and set a new email
     */
    @PutMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public UserProfile updateEmail(@RequestBody @Email @NotBlank String email) throws NoDataFound {
        return service.updateEmail(email);
    }

    /**
     * Deactivate profile by admin and moderator
     */
    @PostMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin', 'moderator')")
    public void deactivateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.deactivateAccount(id);
    }

    /**
     * Activate profile by admin and moderator
     */
    @PostMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin', 'moderator')")
    public void activateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.activateAccount(id);
    }

    /**
     * Delete profile by admin
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteProfileById(@PathVariable @Positive Long id) {
        service.deleteById(id);
    }

    /**
     * Add the current user following.
     */
    @PostMapping("/follows/{followingId}")
    public ResponseEntity<User> addColleague(@PathVariable Long followingId) throws NoDataFound {
        User updatedUser = service.addFollowers( followingId);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Remove the current user following.
     */
    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<User> removeFriend(@PathVariable Long followingId) throws NoDataFound {
        User updatedUser = service.removeFollowers(followingId);
        return ResponseEntity.ok(updatedUser);
    }
}

