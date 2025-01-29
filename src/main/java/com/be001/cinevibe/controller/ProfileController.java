package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.UserProfileDTO;
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
    public ResponseEntity<UserProfileDTO> viewProfile() throws NoDataFound {
        return ResponseEntity.ok(service.getProfile());
    }

    /**
     * View all profiles by user
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileDTO>> viewAllProfiles(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAllProfiles(pageable));
    }

    /**
     * Add the current user's profile's picture or change existing one.
     */
    @PostMapping("/picture")
    public ResponseEntity<UserProfileDTO> updateProfilePicture(@RequestParam("file") MultipartFile file) throws NoDataFound, IOException {
        return ResponseEntity.ok(service.updateProfilePicture(file));
    }

    /**
     * Update the username of the user. It gets user from principal and set a new username
     */
    @PutMapping("/username")
    public ResponseEntity<UserProfileDTO> updateUsername(@RequestBody @NotBlank String username) throws NoDataFound {
        return ResponseEntity.ok(service.updateUsername(username));
    }

    /**
     * Update the email of the user. It takes user from principal and set a new email
     */
    @PutMapping("/email")
    public ResponseEntity<UserProfileDTO> updateEmail(@RequestBody @Email @NotBlank String email) throws NoDataFound {
        return ResponseEntity.ok(service.updateEmail(email));
    }

    /**
     * Deactivate profile by admin and moderator
     */
    @PostMapping("/deactivate/{id}")
    @PreAuthorize("hasAnyAuthority('admin', 'moderator')")
    public ResponseEntity<Void> deactivateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.deactivateAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Activate profile by admin and moderator
     */
    @PostMapping("/activate/{id}")
    @PreAuthorize("hasAnyAuthority('admin', 'moderator')")
    public ResponseEntity<Void> activateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.activateAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Delete profile by admin
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteProfileById(@PathVariable @Positive Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Add the current user following.
     */
    @PostMapping("/follows/{followingId}")
    public ResponseEntity<User> addFollow(@PathVariable Long followingId) throws NoDataFound {
        User updatedUser = service.addFollowers(followingId);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Remove the current user following.
     */
    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<User> removeFollow(@PathVariable Long followingId) throws NoDataFound {
        User updatedUser = service.removeFollowers(followingId);
        return ResponseEntity.ok(updatedUser);
    }
}

