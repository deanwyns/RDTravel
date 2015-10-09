package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.PaymentMethod;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest extends DataSetPersistenceTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookService bookService;
    private Trip trip;

    @Before
    public void initialize() {
        //Trip is defined in data.xml. Start date is 04/10/2015 and end date is 07/10/2015. So there are 3 days.
        //Price is 25.20 per day as defined in the data.xml
        //Number of travelers is 4 as defined in the data.xml
        trip = entityManager().find(Trip.class, 1l);
    }

    @Test
    public void testRegularPriceIsCalculated() {
        Booking booking = entityManager().find(Booking.class, 1l);
        //Payment method is endorsement, so no discounts are applied.
        //The following price should be calculated:
        //Price = number of days * price per day * number of travelers - discount
        //Price = 3 * 25.20 * 4 - 0 = 302.4
        BigDecimal expected = BigDecimal.valueOf(302.4).setScale(2, BigDecimal.ROUND_UNNECESSARY);
        assertEquals(expected, bookService.calculatePrice(booking));
    }

    @Test
    public void testPriceIsDiscountedWithCreditCard() {
        Booking booking = entityManager().find(Booking.class, 2l);
        //Payment method is CreditCard, so no discounts are applied.
        //The following price should be calculated:
        //Price = number of days * price per day * number of travelers - discount
        //Price = 3 * 25.20 * 4 - ((3 * 25.20 * 4)*0.1) = 272.16
        BigDecimal expected = BigDecimal.valueOf(272.16).setScale(2, BigDecimal.ROUND_UNNECESSARY);
        assertEquals(expected, bookService.calculatePrice(booking));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDates(){
        LocalDate endDate = trip.getEndDate();
        LocalDate startDate = trip.getStartDate();

        trip.setEndDate(startDate);
        trip.setStartDate(endDate);

        Booking booking = entityManager().find(Booking.class, 1l);
        booking.setTrip(trip);

        bookService.calculatePrice(booking);
    }
}
