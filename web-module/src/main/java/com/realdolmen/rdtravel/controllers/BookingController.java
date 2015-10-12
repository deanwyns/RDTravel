package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.CreditCard;
import com.realdolmen.rdtravel.views.BookTripViewModel;
import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.Endorsement;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.BookingDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;
import com.realdolmen.rdtravel.services.BookService;
import com.realdolmen.rdtravel.views.BookingViewModel;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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
    private TripDAO tripDAO;
    @Inject
    private Conversation conversation;

    @Inject
    private BookingViewModel vm;

    private String selectedPayment;

    private String previousPage;
    private String totalPrice;

    public String book() {
        conversation.begin();

        vm.setBooking(new Booking());
        vm.getBooking().setPaymentMethod(new CreditCard());

        String participants = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("participants");
        int numberParticipants = Integer.parseInt(participants);
        vm.getBooking().setNumberOfTravelers(numberParticipants);

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trip");
        Trip tripById = tripDAO.read(Long.parseLong(id));
        vm.getBooking().setTrip(tripById);

        return "customer/book?faces-redirect=true";
    }

    public String bookTrip(){
        bookService.createBooking(vm.getBooking());
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
                vm.getBooking().setPaymentMethod(new CreditCard());
                break;

            case "Endorsement":
                vm.getBooking().setPaymentMethod(new Endorsement());
                break;
        }
    }

    public Booking getBooking() {
        return vm.getBooking();
    }

    public void setBooking(Booking booking) {
        vm.setBooking(booking);
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public String getTotalPrice() {
        BigDecimal bigDecimal = bookService.calculatePrice(vm.getBooking());
        return bigDecimal.toPlainString();
    }

    public String getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(String selectedPayment) {
        this.selectedPayment = selectedPayment;
    }
}
