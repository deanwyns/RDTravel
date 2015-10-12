package com.realdolmen.rdtravel.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by JSTAX29 on 2/10/2015.
 * An order of a trip.
 */
@NamedQueries({
        @NamedQuery(name = Booking.COUNT_TOTAL_BOOKINGS_FOR_PARTNER, query = "select count(b) from Booking b join b.trip.flights as f where f.partner.id = :pid"),
        @NamedQuery(name = Booking.COUNT_TOTAL_PARTICIPANTS_FOR_TRIP, query = "select (count(b) * b.numberOfTravelers) from Booking b where b.trip.id = :tid")
})
@Entity
public class Booking {
    public static final String COUNT_TOTAL_BOOKINGS_FOR_PARTNER = "Booking.countTotalBookingsForPartner";
    public static final String COUNT_TOTAL_PARTICIPANTS_FOR_TRIP = "Booking.countTotalParticipantsForTrip";

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

    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    @Digits(fraction = 2, integer = 8)
    private BigDecimal paidPrice;

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

    public BigDecimal getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(BigDecimal paidPrice) {
        this.paidPrice = paidPrice;
    }
}
