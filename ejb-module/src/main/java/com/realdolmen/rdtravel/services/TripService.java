package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.TripDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DWSAX40 on 7/10/2015.
 */
@Named
@RequestScoped
public class TripService {
    @Inject private TripDAO tripDAO;

    /**
     * Get the least available seats of all flights.
     * @param trip
     * @return least available seats
     */
    public int getMaxSeatsForTrip(Trip trip) {
        List<Flight> flights = trip.getFlights();
        if(flights.size() == 0) {
            throw new IllegalArgumentException("The given trip doesn't have any flights.");
        }

        return flights.stream().mapToInt(Flight::getMaxSeats).min().getAsInt();
    }

    /**
     * Find all trips with a given country and with a period between the given dates.
     * @param departureDate
     * @param returnDate
     * @param country
     * @throws IllegalArgumentException the departure date takes place before today, or after the return date.
     * @return trips
     */
    public List<Trip> findBy(LocalDate departureDate, LocalDate returnDate, Country country, int participantsAmount) {
        if(departureDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Departure date can't be before today.");
        }

        if(departureDate.isAfter(returnDate)) {
            throw new IllegalArgumentException("Departure date can't be after return date.");
        }
        System.out.println(tripDAO.findByDestinationCountry(country));
        for(Trip trip : tripDAO.findByDestinationCountry(country)) {
            System.out.println("///////////////////////////////////////");
            System.out.println(trip.getName());
            System.out.println(getMaxSeatsForTrip(trip));
        }

        return tripDAO.findByDestinationCountry(country).stream().filter(
                trip -> getMaxSeatsForTrip(trip) >= participantsAmount && departureDate.isBefore(trip.getStartDate()) && returnDate.isAfter(trip.getEndDate())
        ).collect(Collectors.toList());
    }
}
