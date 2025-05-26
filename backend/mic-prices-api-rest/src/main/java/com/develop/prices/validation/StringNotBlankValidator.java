package com.develop.prices.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringNotBlankValidator implements ConstraintValidator<StringNotBlank, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null) {
      return false;
    }
    return !value.isEmpty();
  }
}
