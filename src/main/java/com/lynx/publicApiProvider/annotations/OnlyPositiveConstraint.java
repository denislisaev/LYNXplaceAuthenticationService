package com.lynx.publicApiProvider.annotations;

import com.lynx.publicApiProvider.validators.OnlyPositiveValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= OnlyPositiveValidator.class)
public @interface OnlyPositiveConstraint {
    String message() default "Value is negative!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
