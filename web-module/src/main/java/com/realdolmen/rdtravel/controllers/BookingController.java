package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.beans.BookTicketBean;
import com.realdolmen.rdtravel.domain.Trip;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@RequestScoped
public class BookingController {

    @Inject
    private BookTicketBean bookTicketBean;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    private Trip trip;


    public BookTicketBean getBookTicketBean() {
        return bookTicketBean;
    }

    public void setBookTicketBean(BookTicketBean bookTicketBean) {
        this.bookTicketBean = bookTicketBean;
    }

    public String bookTicket(){
        return "book?faces-redirect=true&includeViewParams=true";
    }
}
