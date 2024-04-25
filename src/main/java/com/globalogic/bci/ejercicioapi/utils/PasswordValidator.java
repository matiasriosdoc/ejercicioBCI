package com.globalogic.bci.ejercicioapi.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {


    private static boolean containsOneUpperCase(String s){
        return s.matches("^[^A-Z]*[A-Z][^A-Z]*$");
    }

    private static boolean containsTwoDigits(String s){
        return s.matches("^(?:[^\\d]*\\d){2}[^\\d]*$");
    }

    private static boolean between8And12chars(String s){
        return s.matches("^.{8,12}$");
    }

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return containsOneUpperCase(value)
        && containsTwoDigits(value)
        && between8And12chars(value);
    }
}
