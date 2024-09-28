package com.example.linkshorten.contraint;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UrlIsCorrectValidator implements ConstraintValidator<UrlIsCorrect, String> {

    private static final String URL_PATTERN = "^(https)://.*$";

    @Override
    public void initialize(UrlIsCorrect constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url != null) {

            return Pattern.matches(URL_PATTERN, url);
        }
        else {
            return true;
        }
    }
}
