package com.be001.cinevibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String username;
}
