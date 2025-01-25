package com.be001.cinevibe.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank String username, @NotBlank String password, @NotBlank String email) {
}
