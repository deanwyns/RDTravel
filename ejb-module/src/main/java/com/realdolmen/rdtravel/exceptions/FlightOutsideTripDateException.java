package com.realdolmen.rdtravel.exceptions;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class FlightOutsideTripDateException extends Exception {
    public FlightOutsideTripDateException(String msg){
        super(msg);
    }

    public FlightOutsideTripDateException(){
        super("The dates of the provided flight were outside of the dates of the planned trip.");
    }
}
