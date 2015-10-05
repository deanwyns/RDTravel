package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.persistence.AirportDAO;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@ViewScoped
public class AirportSelectionView implements Serializable {
    @Inject
    private AirportDAO airportDAO;

    private String country;

    private List<String> countries = new ArrayList<>();
    private List<Airport> airports = new ArrayList<>();

    @PostConstruct
    public void init() {
        countries = airportDAO.findCountries();
    }

    public void onCountryChange() {
        airports = new ArrayList<>();

        if(country != null && !country.equals("")) {
            airports = airportDAO.findByCountry(country);
        }
    }

    public List<String> getCountries() {
        return countries;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Airport> getAirports() {
        return airports;
    }
}
