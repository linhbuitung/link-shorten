package com.example.linkshorten.contraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordAtLeastFourSpecialCharacterValidator implements ConstraintValidator<PasswordAtLeastFourSpecialCharacter, String> {
    @Override
    public void initialize(PasswordAtLeastFourSpecialCharacter constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        //– at least one lowercase letter
        //– at least two uppercase letters
        //– at least three digits
        //– at least four special characters
        if(password!=null && !password.equals("")){

        int special = password.replaceAll("[A-Za-z0-9\\s]", "").length();

        return ( special >= 4);
        }
        return true;
    }
}


