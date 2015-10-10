package com.realdolmen.rdtravel.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
public class FutureConstraintValidator implements ConstraintValidator<Future, LocalDate>{
    @Override
    public void initialize(Future constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return (value.isAfter(LocalDate.now()));
    }
}
