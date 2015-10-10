package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.*;
import com.realdolmen.rdtravel.services.BookService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private PaymentMethod paymentMethod;

    @Before
    public void initialize() {
        //Trip is defined in data.xml. Start date is 04/10/2015 and end date is 07/10/2015. So there are 3 days.
        //Price is 25.20 per day as defined in the data.xml
        //Number of travelers is 4 as defined in the data.xml
        trip = entityManager().find(Trip.class, 1l);
        paymentMethod = entityManager().find(PaymentMethod.class, 1l);

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

    @Ignore
    @Test
    public void testDefaultMargin() {
        //2 flights are in the database. One of JetAir and one of RyanAir
        //JetAir provides 7% discount when 2 or more tickets have been sold. RyanAir provides no discounts.
        //There are 2 trips. Trip one uses a flight of RyanAir AND JetAir. Trip two uses one flight of JetAir.
        //When calculating the profit of trip 2 without volume sales or seat sales, calculation is as following:
        //Margin = ???
    }

    @Ignore
    @Test
    public void testVolumeSaleOnSeatsMargin() {

    }

    @Ignore
    @Test
    public void testVolumeSaleOnFlightsMargin() {

    }

    @Ignore
    @Test
    public void testVolumeSaleOnFlightAndSeatsMargin() {

    }

    @Ignore
    @Test
    public void testMarginLoss() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDates() {
        LocalDate endDate = trip.getEndDate();
        LocalDate startDate = trip.getStartDate();

        trip.setEndDate(startDate);
        trip.setStartDate(endDate);

        Booking booking = new Booking(4, paymentMethod, trip);
        booking.setTrip(trip);

        bookService.calculatePrice(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSeatsAvailable() {
        Flight flight = entityManager().find(Flight.class, 1l);
        //4 travelers in the booking, 3 seats are left on their flight.
        flight.setOccupiedSeats(flight.getMaxSeats() - 3);
        flushAndClear();

        Booking booking = new Booking(4, paymentMethod, trip);
        bookService.createBooking(booking);
    }

    @Test
    public void testSeatsAreUpdated() {
        Flight flight1 = entityManager().find(Flight.class, 1l);
        Flight flight2 = entityManager().find(Flight.class, 2l);
        flushAndClear();

        Booking booking = new Booking(4, paymentMethod, trip);
        bookService.createBooking(booking);

        assertEquals(4, flight1.getOccupiedSeats());
        assertEquals(4, flight2.getOccupiedSeats());
    }

    @Test
    public void testValidBookingCreatesBookings() {
        PaymentMethod paymentMethod = entityManager().find(PaymentMethod.class, 1l);
        Booking booking = new Booking(2, paymentMethod, trip);

        Mockito.when(bookingDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(bookingDAO.create(booking)).thenCallRealMethod();

        bookService.createBooking(booking);
        flushAndClear();

        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.setEntityManager(entityManager());
        //Three bookings in data.xml, one just added. There should be 4.
        assertEquals(4, bookingDAO.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBookingDoesNotSetSeatsOnFlight() {
        Trip trip = entityManager().find(Trip.class, 1l);
        PaymentMethod paymentMethod = entityManager().find(PaymentMethod.class, 1l);

        Booking booking = new Booking(4, paymentMethod, trip);
        Mockito.when(bookingDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(bookingDAO.create(booking)).thenCallRealMethod();
        //Trip 1 has 2 flights. Flight 1 has 12 available seats, flight 2 has 50.
        //Update flight 2's seats to be nearly full and validate if flight 1 still has 12 available seats.
        Flight flight2 = entityManager().find(Flight.class, 2l);
        flight2.setOccupiedSeats(48);
        flushAndClear();

        bookService.createBooking(booking);

        Flight flight1 = entityManager().find(Flight.class, 1l);
        assertEquals(0, flight1.getOccupiedSeats());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBookingTripHasPassed(){
        trip.setStartDate(LocalDate.now().minusDays(1));
        Booking booking = new Booking(4, paymentMethod, trip);

        bookService.createBooking(booking);
    }

    //todo: fix this.
    @Ignore
    @Test(expected = OptimisticLockException.class)
    public void testTripHasOutdatedFlightInformationDuringBooking() throws Exception {
        //Trip is received in the before method. Flush and clear to close the current transaction
        flushAndClear();
        //Update the flight that is linked to the trip so it no longer has enough available seats
        Flight flight = entityManager().find(Flight.class, 1l);
        flight.setOccupiedSeats(flight.getMaxSeats() - 2);
        flushAndClear();

        //When booking a trip it should not persist to the database and notify with an error
        Booking booking = new Booking(4, paymentMethod, trip);
        Mockito.when(bookingDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(bookingDAO.create(booking)).thenCallRealMethod();
        bookService.createBooking(booking);
    }

}
