package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.beans.BookTripBean;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.TripDAO;
import com.realdolmen.rdtravel.services.BookService;
import com.realdolmen.rdtravel.services.TripService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
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
    @Inject private TripService tripService;
    @Inject private BookTripBean bookTripBean;
    @Inject private Conversation conversation;

    public String book() {
        conversation.begin();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trip");
        bookTripBean.setTrip(tripService.findById(Long.parseLong(id)));

        return "book?faces-redirect=true";
    }

    public BookTripBean getBookTripBean() {
        return bookTripBean;
    }

    public void setBookTripBean(BookTripBean bookTripBean) {
        this.bookTripBean = bookTripBean;
    }
}
