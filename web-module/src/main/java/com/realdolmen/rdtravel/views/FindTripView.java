package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.converters.LocalDateConverter;
import com.realdolmen.rdtravel.domain.Continent;
import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.AirportDAO;
import com.realdolmen.rdtravel.persistence.ContinentDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Named
@ViewScoped
public class FindTripView implements Serializable {
    @Inject private AirportDAO airportDAO;
    @Inject private ContinentDAO continentDAO;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Continent> continents;
    private Continent selectedContinent;
    private Country selectedCountry;
    private List<Country> countries;

    private LocalDate departureDate;
    private LocalDate returnDate;

    @PostConstruct
    public void init() {
        continents = continentDAO.findAll();
    }

    public void onContinentChange() {
        countries = new ArrayList<>(selectedContinent.getCountries());
    }

    public String findTrips() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "trips?faces-redirect=true&" +
                "country=" + selectedCountry.getId() +
                "&departureDate=" + departureDate.format(formatter) +
                "&returnDate=" + returnDate.format(formatter);
    }

    public Continent getSelectedContinent() {
        return selectedContinent;
    }

    public void setSelectedContinent(Continent selectedContinent) {
        this.selectedContinent = selectedContinent;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String departureDateRestriction() {
        if(returnDate == null)
            return LocalDate.now().format(formatter);
        else
            return returnDate.format(formatter);
    }

    public String returnDateRestriction() {
        if(departureDate != null) {
            return departureDate.format(formatter);
        }

        return null;
    }
}
