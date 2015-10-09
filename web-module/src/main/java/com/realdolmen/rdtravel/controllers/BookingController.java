package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.beans.BookTicketBean;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.TripDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@ViewScoped
public class BookingController implements Serializable {
    @Inject private TripDAO tripDAO;

    @Inject
    private BookTicketBean bookTicketBean;

    public String book() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trip");
        trip = tripDAO.read(Long.parseLong(id));

        return "book?faces-redirect=true";
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        System.out.println(trip);
        this.trip = trip;
    }

    private Trip trip;


    public BookTicketBean getBookTicketBean() {
        return bookTicketBean;
    }

    public void setBookTicketBean(BookTicketBean bookTicketBean) {
        this.bookTicketBean = bookTicketBean;
    }
}
