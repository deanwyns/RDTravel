package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.PartnerAdmin;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.persistence.FlightDAO;
import com.realdolmen.rdtravel.services.FlightService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@ViewScoped
public class FlightView implements Serializable {
    @Inject
    private FlightService flightService;
    @Inject
    private AirportSelectionView airportSelectionView;

    @Inject
    private User user;

    private Flight flight = new Flight();

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        if (flight == null)
            return;

        this.flight = flight;

        if (flight.getId() != null) {
            getAirportSelectionView().setDepartureCountry(flight.getDeparture().getCountry().getName());
            getAirportSelectionView().setDestinationCountry(flight.getDestination().getCountry().getName());
            getAirportSelectionView().onDepartureCountryChange();
            getAirportSelectionView().onDestinationCountryChange();
        }
    }

    @Transactional
    public String save() {
        PartnerAdmin partnerAdmin = (PartnerAdmin) user;
        flight.setPartner(partnerAdmin.getPartner());

        try {
            flightService.updateFlight(flight);
            return "overview";
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Flight creation failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }

    public AirportSelectionView getAirportSelectionView() {
        return airportSelectionView;
    }

    public void setAirportSelectionView(AirportSelectionView airportSelectionView) {
        this.airportSelectionView = airportSelectionView;
    }
}
