package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.persistence.FlightDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by JSTAX29 on 14/10/2015.
 */
@Named
@RequestScoped
public class FlightService {
    @Inject
    private FlightDAO flightDAO;

    /**
     * Will update or create the given flight.
     *
     * @param flight the flight to be updated or created
     * @throws IllegalArgumentException when the dates are wrong, the discount values are invalid or when the departure is equal to the destination.
     */
    @Transactional
    public void updateFlight(Flight flight) {

        if (flight.getDepartureTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Flight departure has already passed.");

        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()))
            throw new IllegalArgumentException("Flight departure is after the arrival time.");

        if(flight.getDepartureTime().isEqual(flight.getArrivalTime()))
            throw new IllegalArgumentException("Flight can not arrive at the same time as it leaves.");

        if (flight.isDiscount()) {
            if (flight.getDiscountPercentage() == null)
                throw new IllegalArgumentException("Flight is discounted but no discount percentage is given.");
            if (flight.getSeatsThresholdForDiscount() == null)
                throw new IllegalArgumentException("Flight is discounted but no seat threshold for discount is given.");
        }

        if (flight.getDeparture().equals(flight.getDestination()))
            throw new IllegalArgumentException("The destination is equal to the departure");

        flightDAO.update(flight);
    }
}
