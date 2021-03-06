package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Country;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    @Override
    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }

    @Override
    public List<Country> findAll() {
        return em.createNamedQuery(Country.FIND_ALL, Country.class).getResultList();
    }

    /**
     * Finds all countries with the given ISO2 string
     * @param iso2 the string to search countries on
     * @return the country that corresponds to the iso2 string
     * @throws javax.persistence.NoResultException when no country is found
     */
    public Country findByISO2(String iso2) {
        return em.createNamedQuery(Country.FIND_BY_ISO2, Country.class).setParameter("iso", iso2).getSingleResult();
    }

    /**
     * Finds all countries with the given ISO3 string
     * @param iso3 the string to search countries on
     * @return the country that corresponds to the iso3 string
     * @throws javax.persistence.NoResultException when no country is found
     */
    public Country findByISO3(String iso3) {
        return em.createNamedQuery(Country.FIND_BY_ISO3, Country.class).setParameter("iso", iso3).getSingleResult();
    }
}
