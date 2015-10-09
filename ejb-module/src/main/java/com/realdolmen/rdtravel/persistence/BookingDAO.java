package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.PaymentMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class BookingDAO extends GenericDaoImpl<Booking, Long> {
    @PersistenceContext
    private EntityManager em;

    public BookingDAO() {
        super(Booking.class);
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
