package com.realdolmen.rdtravel.views;

import com.google.gson.Gson;
import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Continent;
import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.persistence.AirportDAO;
import com.realdolmen.rdtravel.persistence.ContinentDAO;
import com.realdolmen.rdtravel.persistence.CountryDAO;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Named
@ViewScoped
public class FindTripView implements Serializable {
    @Inject private AirportDAO airportDAO;
    @Inject private ContinentDAO continentDAO;
    @Inject private CountryDAO countryDAO;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Continent> continents;
    private Continent selectedContinent;
    private Country selectedCountry;
    @NotNull private Airport selectedAirport;
    private List<Country> countries;
    private List<Airport> airports;

    @NotNull private LocalDate departureDate;
    @NotNull private LocalDate returnDate;

    @NotNull @Min(1) private Integer participantsAmount;

    @PostConstruct
    public void init() {
        continents = continentDAO.findAll();
        selectedContinent = continents.get(0);
        onContinentChange();
    }

    public void onContinentChange() {
        countries = new ArrayList<>(selectedContinent.getCountries());
        countries.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));

        selectedCountry = countries.get(0);
        onCountryChange();
    }

    public void onCountryChange() {
        airports = airportDAO.findByCountryName(selectedCountry.getName());
    }

    public String findTrips() {
        return "trips?faces-redirect=true&" +
                "destination=" + selectedAirport.getId() +
                "&departureDate=" + departureDate.format(formatter) +
                "&returnDate=" + returnDate.format(formatter) +
                "&participants=" + participantsAmount;
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

    public Continent getSelectedContinent() {
        return selectedContinent;
    }

    public void setSelectedContinent(Continent selectedContinent) {
        this.selectedContinent = selectedContinent;
    }

    public void selectAirportCommand() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if(params.containsKey("airport") && params.containsKey("continent")) {
            String airportId = params.get("airport");
            Airport airport = airportDAO.read(Long.parseLong(airportId));

            String continentISO2 = params.get("continent");
            selectedContinent = continentDAO.findByISO2(continentISO2);
            selectedCountry = airport.getCountry();
            selectedAirport = airport;
        }
    }

    public void getAirportsCommand() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if(params.containsKey("country")) {
            String countryISO2 = params.get("country");
            List<Airport> airports = airportDAO.findByCountryISO2(countryISO2);

            RequestContext.getCurrentInstance().addCallbackParam("airports", new Gson().toJson(airports));
        }
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

    public Integer getParticipantsAmount() {
        return participantsAmount;
    }

    public void setParticipantsAmount(Integer participantsAmount) {
        this.participantsAmount = participantsAmount;
    }

    public Airport getSelectedAirport() {
        return selectedAirport;
    }

    public void setSelectedAirport(Airport selectedAirport) {
        this.selectedAirport = selectedAirport;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
