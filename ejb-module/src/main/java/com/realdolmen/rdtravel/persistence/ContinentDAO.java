package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Continent;

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
public class ContinentDAO extends GenericDaoImpl<Continent, Integer> {
    @PersistenceContext
    private EntityManager em;

    public ContinentDAO() {
        super(Continent.class);
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
    public List<Continent> findAll() {
        return em.createNamedQuery(Continent.FIND_ALL, Continent.class).getResultList();
    }

    public Continent findByISO2(String iso2) {
        return em.createNamedQuery(Continent.FIND_BY_ISO2, Continent.class).setParameter("iso", iso2).getSingleResult();
    }
}
