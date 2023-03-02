package com.lynx.publicApiProvider.validators;

import com.lynx.publicApiProvider.annotations.PasswordMatches;
import com.lynx.publicApiProvider.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest signUpRequest = (SignUpRequest) o;
        if (signUpRequest.getPassword() == null || signUpRequest.getConfirmPassword() == null){
            return false;
        }
        return signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword());
    }
}