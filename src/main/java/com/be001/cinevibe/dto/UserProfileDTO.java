package com.be001.cinevibe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;


}
