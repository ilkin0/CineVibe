package com.be001.cinevibe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserProfileDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;


    public UserProfileDTO(String email, String username) {
        this.email = email;
        this.username = username;
    }
}