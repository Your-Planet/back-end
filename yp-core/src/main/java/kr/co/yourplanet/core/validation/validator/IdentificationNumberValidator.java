package kr.co.yourplanet.core.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.yourplanet.core.validation.annotation.ValidIdentificationNumber;

public class IdentificationNumberValidator
        implements ConstraintValidator<ValidIdentificationNumber, String> {

    private static final String BUSINESS_NUMBER_REGEX = "^\\d{3}-\\d{2}-\\d{5}$";
    private static final String RESIDENT_NUMBER_REGEX = "^\\d{6}-\\d{7}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches(BUSINESS_NUMBER_REGEX) || value.matches(RESIDENT_NUMBER_REGEX);
    }
}

