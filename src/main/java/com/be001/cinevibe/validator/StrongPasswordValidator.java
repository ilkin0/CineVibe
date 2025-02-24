package com.be001.cinevibe.validator;

import com.be001.cinevibe.annotation.StrongPassword;
import com.be001.cinevibe.dto.RegisterRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, RegisterRequestDTO> {

    private String message;

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RegisterRequestDTO request, ConstraintValidatorContext context) {
        String password = request.password();

        if (password.contains(request.email()) ||
            password.contains("<script>") ||
            password.toLowerCase().contains("password") ||
            password.matches(".*\\d{6,}.*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
