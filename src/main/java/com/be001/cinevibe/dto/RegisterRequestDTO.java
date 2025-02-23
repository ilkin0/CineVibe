package com.be001.cinevibe.dto;

import com.be001.cinevibe.annotation.MatchPassword;
import com.be001.cinevibe.annotation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@StrongPassword
@MatchPassword
public record RegisterRequestDTO(
        @NotBlank(message = "Username cannot be empty.")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
        @Pattern(
                regexp = "^[a-zA-Z ]+$",
                message = "Username can only contain letters and spaces."
        )
        String username,

        @Email(message = "Invalid email format.")
        @NotBlank(message = "Email cannot be empty.")
        String email,

        @NotBlank(message = "Password cannot be empty.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#()])(?!.*(.)\\1{3,})[A-Za-z\\d@$!%*?&#()]{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, one special character (@$!%*?&), and must not have four consecutive identical characters."
        )
        @Pattern(regexp = "^\\S+$", message = "Password cannot contain spaces.")
        String password,

        @NotBlank(message = "Password confirmation cannot be empty.")
        String passwordReplica) {
}
