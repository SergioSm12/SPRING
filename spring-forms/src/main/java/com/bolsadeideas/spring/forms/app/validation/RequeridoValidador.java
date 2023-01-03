package com.bolsadeideas.spring.forms.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequeridoValidador implements ConstraintValidator<Requerido,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value== null || value.isEmpty() || value.isBlank()){
            return  false;
        }
        return false;
    }
}
