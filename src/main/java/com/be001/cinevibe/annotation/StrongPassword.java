package com.be001.cinevibe.annotation;

import com.be001.cinevibe.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "Password is not strong";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
