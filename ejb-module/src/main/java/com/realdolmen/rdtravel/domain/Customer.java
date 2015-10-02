package com.realdolmen.rdtravel.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSTAX29 on 2/10/2015.
 */
public class Customer extends User {
    private List<TripOrder> tripOrders = new ArrayList<>();

    public List<TripOrder> getTripOrders() {
        return tripOrders;
    }
}
