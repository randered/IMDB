package com.randered.imdb.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
public @interface FilterValidation {

    String message() default "Field value should be from list of: ";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
    String[] values();
}
