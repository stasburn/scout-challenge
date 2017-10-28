package com.autoscout24.domain.validation;

import com.autoscout24.domain.Advert;
import com.autoscout24.domain.Fuel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IsValidFuelTypeValidator implements ConstraintValidator<IsFuelTypeSupported, String> {
    @Override
    public void initialize(IsFuelTypeSupported constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  value == null || Arrays.stream(Fuel.values()).anyMatch(t -> t.name().equals(value));
    }
}
