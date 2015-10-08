package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Customer;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
@Stateless
public class CustomerDAO extends GenericDaoImpl<Customer, Long> {
    @PersistenceContext
    private EntityManager em;

    public CustomerDAO() {
        super(Customer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }
}
