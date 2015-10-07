package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.persistence.AirportDAO;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Named
@ViewScoped
public class BookTripView implements Serializable {
    @Inject private AirportDAO airportDAO;

    private String selectedContinent;
    private List<String> countries = new ArrayList<>();

    public void onContinentChange() {

    }

    public void setSelectedContinent(String selectedContinent) {
        this.selectedContinent = selectedContinent;
    }

    public String getSelectedContinent() {
        return selectedContinent;
    }

    public List<String> getContinents() {
        return new ArrayList<>(Constants.continentMap.keySet().stream().map(Constants.Continent::getName).collect(Collectors.toList()));
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}
