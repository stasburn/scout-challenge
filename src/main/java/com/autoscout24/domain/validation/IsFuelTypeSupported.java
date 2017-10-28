package com.autoscout24.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsValidFuelTypeValidator.class })
public @interface IsFuelTypeSupported {
    String message() default "Fuel type is not supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
