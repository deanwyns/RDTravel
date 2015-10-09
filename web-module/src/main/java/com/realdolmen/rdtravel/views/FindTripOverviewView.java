package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.services.TripService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DWSAX40 on 7/10/2015.
 */
@Named
@ViewScoped
public class FindTripOverviewView implements Serializable {
    @Inject private TripService tripService;

    private Country country;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private int participantsAmount;

    private List<Trip> trips;

    public List<Trip> getTrips() throws IOException {
        if(trips == null) {
            try {
                return tripService.findBy(departureDate, returnDate, country, participantsAmount);
            } catch (IllegalArgumentException ex) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.getExternalContext().redirect("index.xhtml");
            }
        }

        return trips;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public int getParticipantsAmount() {
        return participantsAmount;
    }

    public void setParticipantsAmount(int participantsAmount) {
        this.participantsAmount = participantsAmount;
    }
}
