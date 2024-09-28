package com.example.linkshorten.contraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordAtLeastOneLowercaseValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAtLeastOneLowercase {
    String message() default "{com.example.linkshorten.PasswordAtLeastOneLowercase.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
