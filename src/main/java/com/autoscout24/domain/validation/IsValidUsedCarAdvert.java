package com.autoscout24.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsValidUsedCarAdvertValidator.class })
public @interface IsValidUsedCarAdvert {
    String message() default "Address is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
