package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.CreditCard;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
public class CreditCardCRUDTest extends DataSetPersistenceTest {


    private CreditCard creditCard;

    @Before
    public void initialize(){
        creditCard = entityManager().find(CreditCard.class, 1l);
        flushAndClear();
    }

    @Test
    public void testCreditCardExpirationDateOK() {
        creditCard.setExpirationDate(LocalDate.now().plusDays(1));
        entityManager().merge(creditCard);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreditCardExpirationDateOnSameDayNotOk() {
        creditCard.setExpirationDate(LocalDate.now());
        entityManager().merge(creditCard);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreditCardExpirationDateExpired() {
        creditCard.setExpirationDate(LocalDate.now().minusDays(1));
        entityManager().merge(creditCard);
        flushAndClear();
    }

    @Test
    public void testCreditCardValidCode(){
        creditCard.setNumber(4716965610097306l);
        entityManager().merge(creditCard);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreditCardInvalidCode(){
        creditCard.setNumber(61351351l);
        entityManager().merge(creditCard);
        flushAndClear();
    }
}
