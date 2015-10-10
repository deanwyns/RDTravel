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
@NamedQueries({
        @NamedQuery(name = Booking.COUNT_TOTAL_BOOKINGS_FOR_PARTNER, query = "select count(b) from Booking b join b.trip.flights as f where f.partner.id = :pid")
})
@Entity
public class Booking {
    public static final String COUNT_TOTAL_BOOKINGS_FOR_PARTNER = "Booking.countTotalBookingsForPartner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1)
    private Integer numberOfTravelers;

    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private PaymentMethod paymentMethod;

    @Valid
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Trip trip;

    @Version
    private long version;

    protected Booking(){}

    public Booking(int numberOfTravelers, PaymentMethod paymentMethod, Trip trip) {
        this.numberOfTravelers = numberOfTravelers;
        this.paymentMethod = paymentMethod;
        this.trip = trip;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
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
