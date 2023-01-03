package com.bolsadeideas.spring.forms.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = RequeridoValidador.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface Requerido {
    String message() default " El campo  es requerido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
