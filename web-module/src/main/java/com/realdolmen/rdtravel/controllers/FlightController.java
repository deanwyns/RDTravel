package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.persistence.FlightDAO;
import com.realdolmen.rdtravel.views.AirportSelectionView;
import com.realdolmen.rdtravel.views.FlightView;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@RequestScoped
public class FlightController implements Serializable {
    @Inject private FlightDAO flightDAO;
    @Inject private FlightView viewModel;
    @Inject private AirportSelectionView airportSelectionView;

    private Flight selectedFlight;

    public FlightView getViewModel() {
        return viewModel;
    }

    public List<Flight> getFlights() {
        return flightDAO.findAll();
    }

    public void delete(Flight flight) {
        flightDAO.delete(flight);
    }

    public String save() {
        Flight flight;
        if(selectedFlight == null) {
            flight = new Flight();
        } else {
            flight = selectedFlight;
        }

        flight.setArrivalTime(viewModel.getArrivalTime());
        flight.setDepartureTime(viewModel.getDepartureTime());
        flight.setDeparture(viewModel.getDepartureAirport());
        flight.setDestination(viewModel.getDestinationAirport());
        flight.setMaxSeats(viewModel.getAvailableSeats());
        flight.setPrice(viewModel.getPrice());

        if(selectedFlight == null) {
            flightDAO.create(flight);
        } else {
            flightDAO.update(flight);
        }

        return "overview";
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;

        if(selectedFlight == null)
            return;

        viewModel.setArrivalTime(selectedFlight.getArrivalTime());
        viewModel.setDepartureTime(selectedFlight.getDepartureTime());
        viewModel.setAvailableSeats(selectedFlight.getMaxSeats());
        viewModel.setDepartureAirport(selectedFlight.getDeparture());
        viewModel.setDestinationAirport(selectedFlight.getDestination());
        viewModel.setPrice(selectedFlight.getPrice());

        airportSelectionView.setCountry1(selectedFlight.getDeparture().getCountry());
        airportSelectionView.setCountry2(selectedFlight.getDestination().getCountry());
        airportSelectionView.onCountryChange1();
        airportSelectionView.onCountryChange2();
    }

    public AirportSelectionView getAirportSelectionView() {
        return airportSelectionView;
    }

    public void setAirportSelectionView(AirportSelectionView airportSelectionView) {
        this.airportSelectionView = airportSelectionView;
    }
}
