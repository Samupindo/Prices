package com.develop.prices.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
/** Solo la utiliza cuando está en ejecución * */
@Constraint(validatedBy = StringNotBlankValidator.class)
/** Indica la clase a la que va para hacer la validación * */
@Target({ElementType.FIELD})
/** Indica a qué nivel quieres hacer la annotation, en este caso a nivel de campo * */
public @interface StringNotBlank {
  String message() default "must not be blank";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
