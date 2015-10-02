package com.realdolmen.rdtravel.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSTAX29 on 2/10/2015.
 * A customer is a user, but can also make orders.
 */
@Entity
public class Customer extends User {
    @OneToMany
    private List<TripOrder> tripOrders = new ArrayList<>();

    public List<TripOrder> getTripOrders() {
        return tripOrders;
    }
}
