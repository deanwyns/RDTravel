package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Country;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by DWSAX40 on 7/10/2015.
 */
@Named
@RequestScoped
public class CountryDAO extends GenericDaoImpl<Country, Integer> {
    @PersistenceContext
    private EntityManager em;

    public CountryDAO() {
        super(Country.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
