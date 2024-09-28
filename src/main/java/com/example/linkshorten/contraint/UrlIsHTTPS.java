package com.example.linkshorten.contraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UrlIsHTTPSValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlIsHTTPS {
    String message() default "{com.example.linkshorten.UrlIsHTTPS.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
