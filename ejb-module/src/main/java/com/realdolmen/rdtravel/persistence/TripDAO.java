package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Stateless
public class TripDAO extends GenericDaoImpl<Trip, Long> {

    @PersistenceContext
    private EntityManager em;

    public TripDAO() {
        super(Trip.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//    /**
//     * Persists all trips. Rollbacks when an error happens.
//     * todo: write test.
//     *
//     * @param trips the trips to be inserted into the database
//     */
//    @Transactional
//    public void persistAll(List<Trip> trips) {
//        for(Trip trip : trips){
//            for(Flight flight : trip.getFlights()){
//                em.merge(flight);
//            }
//            em.persist(trip);
//        }
//    }

    @Override
    public Trip create(Trip t) {
        for(Flight flight : t.getFlights()){
            em.merge(flight);
        }
        em.persist(t);
        return t;
    }
}
