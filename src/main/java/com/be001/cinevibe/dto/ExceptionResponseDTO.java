package com.be001.cinevibe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    private Integer statusCode;
    private String message;

    public ExceptionResponseDTO(String message) {
        this.message = message;
    }
}
