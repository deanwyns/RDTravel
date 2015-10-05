package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by DWSAX40 on 5/10/2015.
 * An implementation of the generic DAO for flights.
 */
@Stateless
public class FlightDAO extends GenericDaoImpl<Flight, Long> {

    @PersistenceContext
    private EntityManager em;

    public FlightDAO() {
        super(Flight.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
