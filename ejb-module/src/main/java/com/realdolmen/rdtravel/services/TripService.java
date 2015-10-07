package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.domain.Country;
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

    public List<Trip> findByDateAndCountry(LocalDate departureDate, LocalDate returnDate, Country country) {
        return tripDAO.findByCountry(country).stream().filter(
                trip -> departureDate.isBefore(trip.getStartDate()) && returnDate.isAfter(trip.getEndDate())
        ).collect(Collectors.toList());
    }
}
