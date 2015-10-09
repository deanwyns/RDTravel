package com.realdolmen.rdtravel.beans;

import com.realdolmen.rdtravel.domain.Trip;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@ConversationScoped
public class BookTicketBean implements Serializable{

    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
