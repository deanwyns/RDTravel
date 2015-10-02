package com.realdolmen.rdtravel.domain;

/**
 * Created by JSTAX29 on 2/10/2015.
 */
public class TripOrder {
    private Long id;
    private int numberOfTravelers;
    private PaymentMethod paymentMethod;

    protected TripOrder(){}

    public Long getId() {
        return id;
    }

    public int getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(int numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
