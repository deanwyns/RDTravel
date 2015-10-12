package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.PartnerAdmin;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.persistence.FlightDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@ViewScoped
public class FlightView implements Serializable {
    @Inject private FlightDAO flightDAO;
    @Inject private AirportSelectionView airportSelectionView;

    @Inject private User user;

    private Flight flight = new Flight();

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        if(flight == null)
            return;

        this.flight = flight;

        if(flight.getId() != null) {
            getAirportSelectionView().setDepartureCountry(flight.getDeparture().getCountry().getName());
            getAirportSelectionView().setDestinationCountry(flight.getDestination().getCountry().getName());
            getAirportSelectionView().onDepartureCountryChange();
            getAirportSelectionView().onDestinationCountryChange();
        }
    }

    public String save() {
        PartnerAdmin partnerAdmin = (PartnerAdmin)user;
        flight.setPartner(partnerAdmin.getPartner());

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
