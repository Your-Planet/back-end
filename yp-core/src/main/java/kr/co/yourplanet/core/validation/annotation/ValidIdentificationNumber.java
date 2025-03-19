package kr.co.yourplanet.core.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kr.co.yourplanet.core.validation.validator.IdentificationNumberValidator;

@Constraint(validatedBy = IdentificationNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentificationNumber {

    String message() default "유효하지 않은 번호 형식입니다. (예: 주민등록번호 123456-1234567 또는 사업자등록번호 123-45-67890)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}