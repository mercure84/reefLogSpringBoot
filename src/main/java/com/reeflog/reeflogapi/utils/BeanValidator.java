package com.reeflog.reeflogapi.utils;

import com.reeflog.reeflogapi.beans.helpers.SignUpForm;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

// class qui contient les méthodes de validation de cetains beans

@Service
public class BeanValidator {


    public boolean isSignupFormValide (SignUpForm signupForm) {

        boolean isBeanValide = false;
        //Create ValidatorFactory which returns validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        //It validates bean instances
        Validator validator = factory.getValidator();

        //Validate bean
        Set<ConstraintViolation<SignUpForm>> constraintViolations = validator.validate(signupForm);

        //Show errors
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<SignUpForm> violation : constraintViolations) {
                System.out.println(violation.getMessage());
                isBeanValide = false;

            }
        } else {
            System.out.println("Valid Object");
            isBeanValide = true;
        }
        return isBeanValide;
    }
}