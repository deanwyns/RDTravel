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
    @Inject private AirportSelectionView airportSelectionView;

    private Flight flight = new Flight();

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;

        if(flight != null && flight.getId() != null) {
            getAirportSelectionView().setDepartureCountry(flight.getDeparture().getCountry().getName());
            getAirportSelectionView().setDestinationCountry(flight.getDestination().getCountry().getName());
            getAirportSelectionView().onDepartureCountryChange();
            getAirportSelectionView().onDestinationCountryChange();
        }
    }

    public String save() {
        flightDAO.update(flight);

        return "overview";
    }

    public AirportSelectionView getAirportSelectionView() {
        return airportSelectionView;
    }

    public void setAirportSelectionView(AirportSelectionView airportSelectionView) {
        this.airportSelectionView = airportSelectionView;
    }
}
