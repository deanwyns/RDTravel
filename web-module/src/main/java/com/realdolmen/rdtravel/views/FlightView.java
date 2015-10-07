package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.persistence.FlightDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@RequestScoped
public class FlightView implements Serializable {
    @Inject private FlightDAO flightDAO;
    @Inject private FlightViewModel viewModel;
    @Inject private AirportSelectionView airportSelectionView;

    private Flight selectedFlight;

    public FlightViewModel getViewModel() {
        return viewModel;
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

        airportSelectionView.setDepartureCountry(selectedFlight.getDeparture().getCountry().getName());
        airportSelectionView.setDestinationCountry(selectedFlight.getDestination().getCountry().getName());
        airportSelectionView.onDepartureCountryChange();
        airportSelectionView.onDestinationCountryChange();
    }

    public AirportSelectionView getAirportSelectionView() {
        return airportSelectionView;
    }

    public void setAirportSelectionView(AirportSelectionView airportSelectionView) {
        this.airportSelectionView = airportSelectionView;
    }
}
