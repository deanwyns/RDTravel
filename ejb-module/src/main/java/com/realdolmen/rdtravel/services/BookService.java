package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.*;
import com.realdolmen.rdtravel.persistence.BookingDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
     * @param booking the booking to calculate the price on
     * @return the price as a BigDecimal
     */
    public BigDecimal calculatePrice(Booking booking) {
        Trip bookingTrip = booking.getTrip();
        BigDecimal priceOfTrip;
        LocalDate departureDate = bookingTrip.getStartDate();
        LocalDate returnDate = bookingTrip.getEndDate();

        if(departureDate.isAfter(returnDate))
            throw new IllegalArgumentException("Departure date was after return date.");

        long lengthOfTrip = DAYS.between(departureDate, returnDate);

        BigDecimal basePrice = bookingTrip.getPricePerDay().multiply(BigDecimal.valueOf(booking.getNumberOfTravelers())).multiply(BigDecimal.valueOf(lengthOfTrip));

        priceOfTrip = basePrice;

        //If credit card, give discount
        if (booking.getPaymentMethod() instanceof CreditCard){
            BigDecimal discount = basePrice.multiply(BigDecimal.valueOf(0.1));
            priceOfTrip = basePrice.subtract(discount);
        }

        return priceOfTrip.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    }



}