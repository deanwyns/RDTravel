package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Booking;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
@Named
@ViewScoped
public class BookView implements Serializable {
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
