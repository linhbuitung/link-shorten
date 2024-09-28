package com.example.linkshorten.contraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordAtLeastThreeNumberValidator implements ConstraintValidator<PasswordAtLeastThreeNumber, String> {
    @Override
    public void initialize(PasswordAtLeastThreeNumber constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password!=null && !password.equals("")){

            int number = 0;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isDigit(c)) {
                    number++;
                }
            }

            return (number >= 3 );
        }
        return true;
    }
}
