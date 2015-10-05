package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.persistence.FlightDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@RequestScoped
public class FlightController {
    @Inject
    private FlightDAO flightDAO;

    public List<Flight> findAll() {
        return flightDAO.findAll();
    }

    public Flight findById(long id) {
        return flightDAO.read(id);
    }

    public Flight create(Flight flight) {
        return flightDAO.create(flight);
    }

    public Flight update(Flight flight) {
        return flightDAO.update(flight);
    }
}
