package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.CreditCard;
import com.realdolmen.rdtravel.domain.Endorsement;
import com.realdolmen.rdtravel.domain.PaymentMethod;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class BookingCRUDTest extends DataSetPersistenceTest {

    private BookingDAO bookingDAO = new BookingDAO();
    private Booking booking;

    @Before
    public void initialize() {
        bookingDAO.setEntityManager(entityManager());
        booking = bookingDAO.read(1l);
        flushAndClear();
    }

    @Test
    public void testCreateBooking() {
        PaymentMethod paymentMethod = entityManager().find(PaymentMethod.class, 1l);
        entityManager().persist(paymentMethod);
        Booking booking = new Booking(3, paymentMethod);
        assertNull(booking.getId());
        bookingDAO.create(booking);
        flushAndClear();
        assertNotNull(booking.getId());
    }

    @Test
    public void testUpdateBooking() {
        int revisionedvalue = 1;
        booking.setNumberOfTravelers(revisionedvalue);
        bookingDAO.update(booking);
        flushAndClear();
        Booking changedBooking = bookingDAO.read(1l);
        assertEquals(revisionedvalue, changedBooking.getNumberOfTravelers());
        flushAndClear();
    }

//    @Test
//    public void testDeleteBooking() {
//        bookingDAO.delete(booking);
//        flushAndClear();
//        Booking oldBooking = bookingDAO.read(1l);
//        assertNull(oldBooking);
//    }

    @Test(expected = ConstraintViolationException.class)
    public void testPaymentMethodIsNull() {
        booking.setPaymentMethod(null);
        bookingDAO.update(booking);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPaymentMethodIsInvalid() {
        CreditCard creditCard = entityManager().find(CreditCard.class, 1l);
        creditCard.setNumber(null);
        flushAndClear();
        booking.setPaymentMethod(creditCard);
        bookingDAO.update(booking);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNumberOfTravelersIs0(){
        booking.setNumberOfTravelers(0);
        bookingDAO.update(booking);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNumberOfTravelersIsNegative(){
        booking.setNumberOfTravelers(-1);
        bookingDAO.update(booking);
        flushAndClear();
    }
}
