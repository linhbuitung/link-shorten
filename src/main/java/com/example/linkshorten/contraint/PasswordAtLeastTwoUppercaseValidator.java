package com.example.linkshorten.contraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordAtLeastTwoUppercaseValidator implements ConstraintValidator<PasswordAtLeastTwoUppercase, String> {
    @Override
    public void initialize(PasswordAtLeastTwoUppercase constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password!=null && !password.equals("")){

            int upperCase = 0;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    upperCase++;
                }
            }

            return (upperCase >= 2 );
        }
        return true;
    }
}
