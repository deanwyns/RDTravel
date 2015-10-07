package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Country;

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

    @Override
    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }

    public List<Airport> findByCountryName(String countryName) {
        TypedQuery<Airport> query = em.createNamedQuery("airport.findByCountryName", Airport.class).setParameter("country", countryName);
        return query.getResultList();
    }
}
