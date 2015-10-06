package com.realdolmen.rdtravel.views;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Named
@ViewScoped
public class BookTripView {
    private String selectedContinent;

    public void onContinentChange() {

    }

    public void setSelectedContinent(String selectedContinent) {
        this.selectedContinent = selectedContinent;
    }

    public String getSelectedContinent() {
        return selectedContinent;
    }
}
