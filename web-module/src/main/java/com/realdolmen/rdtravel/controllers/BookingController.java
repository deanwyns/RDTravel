package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.CreditCard;
import com.realdolmen.rdtravel.views.BookTripViewModel;
import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.Endorsement;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.BookingDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;
import com.realdolmen.rdtravel.services.BookService;

import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@ViewScoped
public class BookingController implements Serializable {
    @Inject
    private BookService bookService;
    @Inject
    private BookingDAO bookingDAO;
    @Inject
    private TripDAO tripDAO;
    @Inject
    private BookTripViewModel bookTripViewModel;
    @Inject
    private Conversation conversation;

    private String previousPage;
    private String totalPrice;

    public String book() {
        conversation.begin();

        String participants = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("participants");
        int numberParticipants = Integer.parseInt(participants);
        bookTripViewModel.setParticipants(numberParticipants);

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trip");
        Trip tripById = tripDAO.read(Long.parseLong(id));
        bookTripViewModel.setTrip(tripById);

        bookTripViewModel.setBooking(new Booking(numberParticipants, new CreditCard(), tripById));

        return "customer/book?faces-redirect=true";
    }

    public String bookTrip(){

        return "customer/thankyoupage?faces-redirect=true";
    }

    public String cancel() {
        conversation.end();
        return previousPage + "?faces-redirect=true&includeViewParams=true";
    }

    public BookTripViewModel getBookTripViewModel() {
        return bookTripViewModel;
    }

    public void setBookTripViewModel(BookTripViewModel bookTripViewModel) {
        this.bookTripViewModel = bookTripViewModel;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public String getTotalParticipants() {
        Long participants = bookingDAO.countBookedParticipantsForTrip(bookTripViewModel.getTrip().getId());
        return participants.toString();
    }

    public String getTotalPrice() {
        BigDecimal bigDecimal = bookService.calculatePrice(bookTripViewModel.getBooking());
        return bigDecimal.toPlainString();
    }
}
