package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.CreditCard;
import com.realdolmen.rdtravel.domain.Endorsement;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.BookingDAO;
import com.realdolmen.rdtravel.services.BookService;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@ConversationScoped
public class BookTripViewModel implements Serializable{

    private Trip trip;
    private Booking booking;
    private int participants;
    private CreditCard creditCard;
    private Endorsement endorsement;
    private String definePaymentMethod;
    private String paymentMethod;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public CreditCard getCreditCard() {
        return booking.getPaymentMethod() instanceof CreditCard ? (CreditCard) booking.getPaymentMethod() : null;
    }

    public Endorsement getEndorsement() {
        return booking.getPaymentMethod() instanceof Endorsement ? (Endorsement) booking.getPaymentMethod() : null;
    }

    public void definePaymentMethod(String paymentMethod) {
        System.out.println("----------------DEFINE------------------");
        System.out.println(paymentMethod);
        System.out.println("-----------------DEFINE----------------");
        switch (paymentMethod){
            case ("CreditCard"):
                break;
            case ("Endorsement"):
                break;
            default:
                break;
        }
    }

    public void setPaymentMethod(String paymentMethod) {
        System.out.println("----------------SET------------------");
        System.out.println(paymentMethod);
        System.out.println("-----------------SET----------------");
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        System.out.println("----------------GET------------------");
        System.out.println(paymentMethod);
        System.out.println("-----------------GET----------------");
        return paymentMethod;
    }
}
