package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Booking;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by JSTAX29 on 12/10/2015.
 */
@Named
@ConversationScoped
public class BookingViewModel implements Serializable{
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
