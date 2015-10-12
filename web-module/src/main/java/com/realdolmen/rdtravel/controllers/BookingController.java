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
import javax.enterprise.context.ConversationScoped;
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
@ConversationScoped
public class BookingController implements Serializable {
    @Inject
    private BookService bookService;
    @Inject
    private BookingDAO bookingDAO;
    @Inject
    private TripDAO tripDAO;
    @Inject
    private Conversation conversation;

    private Booking booking;
    private String selectedPayment;

    private String previousPage;
    private String totalPrice;

    public String book() {
        conversation.begin();

        booking = new Booking();
        booking.setPaymentMethod(new CreditCard());

        String participants = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("participants");
        int numberParticipants = Integer.parseInt(participants);
        booking.setNumberOfTravelers(numberParticipants);

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trip");
        Trip tripById = tripDAO.read(Long.parseLong(id));
        booking.setTrip(tripById);

        return "customer/book?faces-redirect=true";
    }

    public String bookTrip(){
        conversation.end();
        return "customer/thankyoupage?faces-redirect=true";
    }

    public String cancel() {
        conversation.end();
        return previousPage + "?faces-redirect=true&includeViewParams=true";
    }

    public void onPaymentMethodChange() {
        System.out.println(selectedPayment);
        switch(selectedPayment) {
            case "CreditCard":
                booking.setPaymentMethod(new CreditCard());
                break;

            case "Endorsement":
                booking.setPaymentMethod(new Endorsement());
                break;
        }
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public String getTotalPrice() {
        BigDecimal bigDecimal = bookService.calculatePrice(booking);
        return bigDecimal.toPlainString();
    }

    public String getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(String selectedPayment) {
        this.selectedPayment = selectedPayment;
    }
}
