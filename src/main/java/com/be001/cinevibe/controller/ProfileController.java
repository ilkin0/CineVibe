package com.be001.cinevibe.controller;

import com.be001.cinevibe.dto.UserProfile;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService service;

    /**
     * View the current user's profile.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserProfile viewProfile() throws NoDataFound {
        return service.getProfile();
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
}

