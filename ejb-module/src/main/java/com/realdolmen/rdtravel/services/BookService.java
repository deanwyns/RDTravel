package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.*;
import com.realdolmen.rdtravel.persistence.BookingDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@RequestScoped
public class BookService {
    @Inject
    private BookingDAO bookingDAO;

    /**
     * Calculates the price for a booking based on its length, price per day, number of travelers and payment method which can give a discount.
     * Price = number of days * price per day * number of travelers - discount
     *
     * @param booking the booking to calculate the price on
     * @return the price as a BigDecimal
     */
    public BigDecimal calculatePrice(Booking booking) {
        Trip bookingTrip = booking.getTrip();
        BigDecimal priceOfTrip;
        LocalDate departureDate = bookingTrip.getStartDate();
        LocalDate returnDate = bookingTrip.getEndDate();

        validateDates(booking);

        long lengthOfTrip = DAYS.between(departureDate, returnDate);

        BigDecimal basePrice = bookingTrip.getPricePerDay().multiply(BigDecimal.valueOf(booking.getNumberOfTravelers())).multiply(BigDecimal.valueOf(lengthOfTrip));

        priceOfTrip = basePrice;

        //If credit card, give discount
        if (booking.getPaymentMethod() instanceof CreditCard) {
            BigDecimal discount = basePrice.multiply(BigDecimal.valueOf(0.1));
            priceOfTrip = basePrice.subtract(discount);
        }

        return priceOfTrip.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    }

    /**
     * Will validate the given booking and persist it to the database if everything is valid.
     *
     * @param booking the booking to be validated and persisted
     * @throws ConstraintViolationException if the CreditCard details are invalid
     * @throws IllegalArgumentException     if the dates of the trip are invalid or when no more seats are available
     */
    public void createBooking(Booking booking) {
        validateDates(booking);

        //todo: find out if there may be concurrency issues when editing flights that were retrieved before.
        for (Flight flight : booking.getTrip().getFlights()) {
            //Validate if there is enough space on the flight for the booking
            if (booking.getNumberOfTravelers() > (flight.getMaxSeats() - flight.getOccupiedSeats()))
                throw new IllegalArgumentException(String.format("There were not enough seats available on flight %d. Required seats: %d, available seats: %d", flight.getId(), booking.getNumberOfTravelers(), (flight.getMaxSeats() - flight.getOccupiedSeats())));
            //Update the flight for the count of participants.
            flight.setOccupiedSeats(flight.getOccupiedSeats() + booking.getNumberOfTravelers());
        }

        //All was valid. Persist the booking to the database
        bookingDAO.create(booking);
    }

    /**
     * Validates the dates of a trip
     *
     * @param booking the booking containing the trip to be booked
     */
    private void validateDates(Booking booking) {
        //Validate the trip
        Trip bookingTrip = booking.getTrip();

        LocalDate departureDate = bookingTrip.getStartDate();
        LocalDate returnDate = bookingTrip.getEndDate();

        //The departure was after the return date.
        if (departureDate.isAfter(returnDate))
            throw new IllegalArgumentException("Departure date was after return date.");
        //Departure was before today
        if (departureDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("The departure date has already passed.");
    }

//    /**
//     * Calculates the profit margin for the given booking. This takes seat threshold and volume sales into account.
//     * @param booking the booking to calculate the profit on
//     */
//    public BigDecimal calculateMargin(Booking booking){
//        We're not really sure how the margin is calculated atm
//
//        return BigDecimal.ZERO;
//    }
}