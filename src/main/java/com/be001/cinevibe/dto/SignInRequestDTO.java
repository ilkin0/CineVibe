package com.be001.cinevibe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequestDTO(@NotBlank String username,
                               @Size(min = 8) String password) {
}
