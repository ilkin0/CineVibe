package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
     * Update the current user's profile.
     */
    @PostMapping("/update")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody @Valid UserProfileDTO profileInfo, HttpServletRequest request) {
        return service.updateProfile(profileInfo, request);
    }

    /**
     * Deactivate profile by admin and moderator
     */
    @PostMapping("/deactivate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deactivateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.deactivateAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Activate profile by admin and moderator
     */
    @PostMapping("/activate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> activateProfile(@PathVariable @Positive Long id) throws NoDataFound {
        service.activateAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Delete profile by admin
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProfileById(@PathVariable @Positive Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Add the current user following.
     */
    @PostMapping("/follows/{followingId}")
    public ResponseEntity<UserProfileDTO> addFollow(@PathVariable Long followingId) throws NoDataFound {
        UserProfileDTO updatedUser = service.addFollowers(followingId);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Remove the current user following.
     */
    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<UserProfileDTO> removeFollow(@PathVariable Long followingId) throws NoDataFound {
        UserProfileDTO updatedUser = service.removeFollowers(followingId);
        return ResponseEntity.ok(updatedUser);
    }
}

