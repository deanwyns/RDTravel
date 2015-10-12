package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.CreditCard;
import com.realdolmen.rdtravel.domain.Endorsement;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by JSTAX29 on 12/10/2015.
 */
@Named
@ConversationScoped
public class BookingViewModel implements Serializable{
    @Valid
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public CreditCard getCreditCard() {
        return (CreditCard) booking.getPaymentMethod();
    }

    public void setCreditCard() {
        // sdfioiiogsgji
    }

    public Endorsement getEndorsement() {
        return (Endorsement) booking.getPaymentMethod();
    }

    public void setEndorsement() {
        ///gffiodqsjgogjfqogi
    }
}
