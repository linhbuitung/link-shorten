package com.example.linkshorten.contraint;

import com.example.linkshorten.repositories.URLRepository;
import com.example.linkshorten.services.URLService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlIsUniqueValidator implements ConstraintValidator<UrlIsUnique, String> {
    @Autowired
    URLRepository urlRepository;

    @Override
    public void initialize(UrlIsUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if(url!=null){
            return  urlRepository.findByTargetURLOrRedirectURL(url).isEmpty();
        } else {
            return true;
        }

    }
}
