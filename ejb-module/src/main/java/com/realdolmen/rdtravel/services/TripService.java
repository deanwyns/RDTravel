package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.Airport;
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

    public Trip findById(Long id) {
        return tripDAO.read(id);
    }

    public List<Trip> findAll() { return tripDAO.findAll(); }

    /**
     * Get the least available seats of all flights.
     * @param trip
     * @return least available seats
     */
    public int getAvailableSeatsForTrip(Trip trip) {
        List<Flight> flights = trip.getFlights();
        if(flights.size() == 0) {
            throw new IllegalArgumentException("The given trip doesn't have any flights.");
        }

        return flights.stream().mapToInt(flight -> flight.getMaxSeats() - flight.getOccupiedSeats()).min().getAsInt();
    }

    /**
     * Find all trips with a given airport and with a period between the given dates.
     * @param departureDate
     * @param returnDate
     * @param airport
     * @throws IllegalArgumentException the departure date takes place before today, or after the return date.
     * @return trips
     */
    public List<Trip> findBy(LocalDate departureDate, LocalDate returnDate, Airport airport, int participantsAmount) {
        if(departureDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Departure date can't be before today.");
        }

        if(departureDate.isAfter(returnDate)) {
            throw new IllegalArgumentException("Departure date can't be after return date.");
        }

        return tripDAO.findByDestinationAirport(airport).stream().filter(
                // Filter all trips with enough available seats for the amount of travelers, and all trips within the given dates.
                trip -> getAvailableSeatsForTrip(trip) >= participantsAmount && departureDate.isBefore(trip.getStartDate()) && returnDate.isAfter(trip.getEndDate())
        ).collect(Collectors.toList());
    }
}
