package com.be001.cinevibe.validator;

import com.be001.cinevibe.annotation.MatchPassword;
import com.be001.cinevibe.dto.RegisterRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, RegisterRequestDTO> {

    private String message;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RegisterRequestDTO request, ConstraintValidatorContext context) {
        boolean isValid = request.password().equals(request.passwordReplica());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
