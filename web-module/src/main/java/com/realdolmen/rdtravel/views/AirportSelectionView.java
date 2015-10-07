package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.persistence.AirportDAO;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@ViewScoped
public class AirportSelectionView implements Serializable {
    @Inject
    private AirportDAO airportDAO;

    private String departureCountry, destinationCountry;

    private List<Country> countries = new ArrayList<>();

    private List<Airport> departureAirports = new ArrayList<>();
    private List<Airport> destinationAirports = new ArrayList<>();

    @PostConstruct
    public void init() {
        countries = airportDAO.findCountries();
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void onDepartureCountryChange() {
        departureAirports = new ArrayList<>();

        if(departureCountry != null && !departureCountry.equals("")) {
            departureAirports = airportDAO.findByCountryName(departureCountry);
        }
    }

    public void onDestinationCountryChange() {
        destinationAirports = new ArrayList<>();

        if(destinationCountry != null && !destinationCountry.equals("")) {
            destinationAirports = airportDAO.findByCountryName(destinationCountry);
        }
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public List<Airport> getDepartureAirports() {
        return departureAirports;
    }

    public void setDepartureAirports(List<Airport> departureAirports) {
        this.departureAirports = departureAirports;
    }

    public List<Airport> getDestinationAirports() {
        return destinationAirports;
    }

    public void setDestinationAirports(List<Airport> destinationAirports) {
        this.destinationAirports = destinationAirports;
    }
}
