package com.example.linkshorten.contraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordMinTenCharacterValidator implements ConstraintValidator<PasswordMinTenCharacter, String> {
    @Override
    public void initialize(PasswordMinTenCharacter constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password!=null && !password.equals("")){
            return password.length()>=10;
        }
        return true;
    }
}
