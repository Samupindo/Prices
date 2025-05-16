package com.develop.prices.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringNotBlankValidator.class)
@Target({ElementType.FIELD})

public @interface StringNotBlank {
  String message() default "must not be blank";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
