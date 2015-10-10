package com.realdolmen.rdtravel.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
public class CreditCardNumberValidator implements ConstraintValidator<CreditCardNumber, Long> {
    @Override
    public void initialize(CreditCardNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(value == null)
            return false;

        String temp = Long.toString(value);
        int[] newGuess = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++)
        {
            newGuess[i] = temp.charAt(i) - '0';
        }

        return check(newGuess);
    }

    private boolean check(int[] digits) {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {

            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
}
