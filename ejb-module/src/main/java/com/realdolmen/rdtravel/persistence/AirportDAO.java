package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by JSTAX29 on 4/10/2015.
 * An implementation of the generic DAO for airports.
 */
@Stateless
public class AirportDAO extends GenericDaoImpl<Airport, Long> {

    @PersistenceContext
    private EntityManager em;

    public AirportDAO() {
        super(Airport.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Airport> findByCountry(String country) {
        TypedQuery<Airport> query = em.createNamedQuery("airport.findByCountry", Airport.class).setParameter("country", country);
        return query.getResultList();
    }

    public List<String> findCountries() {
        TypedQuery<String> query = em.createNamedQuery("airport.getCountries", String.class);
        return query.getResultList();
    }
}
