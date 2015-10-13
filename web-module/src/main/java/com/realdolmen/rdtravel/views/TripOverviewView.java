package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.services.TripService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by DWSAX40 on 13/10/2015.
 */
@Named
@RequestScoped
public class TripOverviewView {
    @Inject private TripService tripService;

    private List<Trip> trips;
    private List<Trip> filteredTrips;
    private List<Trip> selectedTrips;

    @PostConstruct
    public void init() {
        trips = tripService.findAll();
    }

    public int getAvailableSeats(Trip trip) {
        return tripService.getAvailableSeatsForTrip(trip);
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getFilteredTrips() {
        return filteredTrips;
    }

    public void setFilteredTrips(List<Trip> filteredTrips) {
        this.filteredTrips = filteredTrips;
    }

    public List<Trip> getSelectedTrips() {
        return selectedTrips;
    }

    public void setSelectedTrips(List<Trip> selectedTrips) {
        this.selectedTrips = selectedTrips;
    }
}
