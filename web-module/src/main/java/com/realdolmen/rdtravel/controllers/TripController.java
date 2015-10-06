package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Trip;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Named
@RequestScoped
public class TripController {

    @Produces({MediaType.APPLICATION_XML})
    public List<Trip> listAllSamples() {
        final List<Trip> trips = new ArrayList<>();
        trips.add(createTrip());
        trips.add(createTrip());
        trips.add(createTrip());
        return trips;
    }

    private Trip createTrip() {
        Trip trip = new Trip();
        trip.setName("Example Trip");
        return trip;
    }
}
