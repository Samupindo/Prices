package com.develop.prices.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatchPutNotBlankValidator implements ConstraintValidator<PatchPutNotBlank, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return !value.isEmpty();
    }
}
