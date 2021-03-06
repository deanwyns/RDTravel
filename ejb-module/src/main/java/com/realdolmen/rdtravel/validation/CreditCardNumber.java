package com.realdolmen.rdtravel.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreditCardNumberValidator.class)
public @interface CreditCardNumber {
    String message() default "This number was not a valid credit card number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
