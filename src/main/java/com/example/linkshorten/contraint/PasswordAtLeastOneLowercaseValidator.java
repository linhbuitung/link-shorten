package com.example.linkshorten.contraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordAtLeastOneLowercaseValidator implements ConstraintValidator<PasswordAtLeastOneLowercase, String> {
    @Override
    public void initialize(PasswordAtLeastOneLowercase constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password!=null && !password.equals("")){

            int lowerCase = 0;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isLowerCase(c)) {
                    lowerCase++;
                }
            }

            return (lowerCase >= 1 );
        }
        return true;
    }
}
