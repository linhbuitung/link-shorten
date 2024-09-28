package com.example.linkshorten.contraint;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlIsHTTPSValidator implements ConstraintValidator<UrlIsHTTPS, String> {

    @Override
    public void initialize(UrlIsHTTPS constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url != null) {

            return url.startsWith("https://") ;
        }
        else {
            return true;
        }
    }
}
