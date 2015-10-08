package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.domain.Trip;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by JSTAX29 on 2/10/2015.
 * An order of a trip.
 */
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Min(value = 1)
    private int numberOfTravelers;
    @Valid
    @NotNull
    @ManyToOne
    private PaymentMethod paymentMethod;

    @Valid
    @NotNull
    @Column(nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Trip trip;

    @Version
    private long version;

    protected Booking(){}

    public Booking(int numberOfTravelers, PaymentMethod paymentMethod) {
        this.numberOfTravelers = numberOfTravelers;
        this.paymentMethod = paymentMethod;
    }

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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
