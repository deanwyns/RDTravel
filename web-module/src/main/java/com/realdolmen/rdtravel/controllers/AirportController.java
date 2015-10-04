package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.persistence.AirportDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JSTAX29 on 4/10/2015.
 */
@Named
@RequestScoped
public class AirportController {
    @Inject
    private AirportDAO airportDAO;

    private List<Airport> airports;

    @PostConstruct
    public void init() {
        airports = airportDAO.findAll();
    }

    public String getAirports() {
        StringBuilder sb = new StringBuilder();
        for (Airport s : airports)
            sb.append(s).append(System.lineSeparator());
        return sb.toString();
    }
}
