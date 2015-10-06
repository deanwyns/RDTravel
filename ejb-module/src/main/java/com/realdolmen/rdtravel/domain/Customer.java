package com.realdolmen.rdtravel.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by JSTAX29 on 2/10/2015.
 * A customer is a user, but can also make orders.
 */
@Entity
public class Customer extends User {
    @OneToMany
    private Collection<Booking> bookings = new ArrayList<>();

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}
