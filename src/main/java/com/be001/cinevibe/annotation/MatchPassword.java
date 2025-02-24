package com.be001.cinevibe.annotation;

import com.be001.cinevibe.validator.MatchPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPassword {

    String message() default "Passwords must be the same. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
