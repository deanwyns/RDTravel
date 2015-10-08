package com.realdolmen.rdtravel.exceptions;

/**
 * Created by JSTAX29 on 7/10/2015.
 */
public class TripNotFoundException extends Exception {
    public TripNotFoundException(String msg) {
        super(msg);
    }

    public TripNotFoundException() {
        super("The trip was not found.");
    }
}
