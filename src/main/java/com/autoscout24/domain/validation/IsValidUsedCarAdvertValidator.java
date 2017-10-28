package com.autoscout24.domain.validation;

import com.autoscout24.domain.Advert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidUsedCarAdvertValidator implements ConstraintValidator<IsValidUsedCarAdvert, Advert> {
    @Override
    public void initialize(IsValidUsedCarAdvert constraintAnnotation) {

    }

    @Override
    public boolean isValid(Advert value, ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean isValid = true;
        if (value.isNew()) {
            if (value.getMileage() > 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("New car cant have mileage")
                        .addPropertyNode("mileage").addConstraintViolation();
                isValid = false;
            }
            if (value.getFirstRegistration() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("New car cant have first registration")
                        .addPropertyNode("firstRegistration").addConstraintViolation();
                isValid = false;
            }
            return isValid;

        } else {
            if (value.getMileage() <= 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Used car should have some mileage")
                        .addPropertyNode("mileage").addConstraintViolation();
                isValid = false;
            }
            if (value.getFirstRegistration() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Used car should have first registration")
                        .addPropertyNode("firstRegistration").addConstraintViolation();
                isValid = false;
            }
            return isValid;
        }
    }
}
