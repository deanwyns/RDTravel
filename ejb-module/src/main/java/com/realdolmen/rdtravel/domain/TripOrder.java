package com.realdolmen.rdtravel.domain;

import javax.persistence.*;

/**
 * Created by JSTAX29 on 2/10/2015.
 * An order of a trip.
 */
@Entity
public class TripOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfTravelers;
    @Enumerated(EnumType.STRING)
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
