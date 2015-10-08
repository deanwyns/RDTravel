package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.PartnerAdmin;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.persistence.FlightDAO;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Named
@ViewScoped
public class FlightOverviewView implements Serializable {
    @Inject private FlightDAO flightDAO;

    @Inject private User user;

    private List<Flight> flights;
    private List<Flight> filteredFlights;
    private List<Flight> selectedFlights;

    @PostConstruct
    public void init() {
        flights = flightDAO.findByPartner(((PartnerAdmin)user).getPartner());
    }

    public void delete(Flight flight) {
        flights.remove(flight);
        flightDAO.delete(flight);
    }

    public boolean filterByTimeBefore(Object value, Object filter, Locale locale) {
        if(filter == null) {
            return true;
        }

        LocalDateTime valueTime = LocalDateTime.parse(value.toString());
        LocalDateTime filterTime = LocalDateTime.parse(filter.toString());

        return filterTime.isBefore(valueTime);
    }

    public boolean filterByTimeAfter(Object value, Object filter, Locale locale) {
        if(filter == null) {
            return true;
        }

        LocalDateTime valueTime = LocalDateTime.parse(value.toString());
        LocalDateTime filterTime = LocalDateTime.parse(filter.toString());

        return filterTime.isAfter(valueTime);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFilteredFlights() {
        return filteredFlights;
    }

    public void setFilteredFlights(List<Flight> filteredFlights) {
        this.filteredFlights = filteredFlights;
    }

    public List<Flight> getSelectedFlights() {
        return selectedFlights;
    }

    public void setSelectedFlights(List<Flight> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }
}
