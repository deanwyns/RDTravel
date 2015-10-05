package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Airport;
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

    private String country1, country2;

    private List<String> countries = new ArrayList<>();

    private List<Airport> airports1 = new ArrayList<>();
    private List<Airport> airports2 = new ArrayList<>();

    @PostConstruct
    public void init() {
        countries = airportDAO.findCountries();
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public void onCountryChange1() {
        airports1 = new ArrayList<>();

        if(country1 != null && !country1.equals("")) {
            airports1 = airportDAO.findByCountry(country1);
        }
    }

    public void onCountryChange2() {
        airports2 = new ArrayList<>();

        if(country2 != null && !country2.equals("")) {
            airports2 = airportDAO.findByCountry(country2);
        }
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public List<Airport> getAirports1() {
        return airports1;
    }

    public void setAirports1(List<Airport> airports1) {
        this.airports1 = airports1;
    }

    public List<Airport> getAirports2() {
        return airports2;
    }

    public void setAirports2(List<Airport> airports2) {
        this.airports2 = airports2;
    }
}
