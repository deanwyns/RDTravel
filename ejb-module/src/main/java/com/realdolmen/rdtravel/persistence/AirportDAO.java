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

    @Override
    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }

    @Override
    public List<Airport> findAll() {
        return em.createNamedQuery(Airport.FIND_ALL, Airport.class).getResultList();
    }

    public List<Airport> findByCountryName(String countryName) {
        TypedQuery<Airport> query = em.createNamedQuery(Airport.FIND_BY_COUNTRY_NAME, Airport.class).setParameter("country", countryName);
        return query.getResultList();
    }
}
