package com.develop.prices.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNotNullValidator.class)
@Target({ElementType.FIELD})
public @interface PhoneNotNull {
    String message() default "Fields misentered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}