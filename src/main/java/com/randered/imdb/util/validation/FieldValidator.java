package com.randered.imdb.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public final class FieldValidator implements ConstraintValidator<FilterValidation, String> {

    private Set<String> pattern;

    private String expression;

    @Override
    public void initialize(FilterValidation constraintAnnotation) {
        pattern = Set.of(constraintAnnotation.values());
        expression = constraintAnnotation.message().concat(String.join(",", expression));
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        boolean valid = null == value || pattern.contains(value) || value.isEmpty();

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(expression).addConstraintViolation();
        }

        return valid;
    }
}
