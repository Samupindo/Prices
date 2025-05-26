package com.develop.prices.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNotNullValidator implements ConstraintValidator<PhoneNotNull, Integer> {

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null || value.toString().isEmpty()) {
      return false;
    }

    String phoneStr = String.valueOf(value);

    return phoneStr.length() == 9 && value > 0;
  }
}
