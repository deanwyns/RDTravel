package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.Trip;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
@Named
@RequestScoped
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

    /**
     * Counts the number of sold bookings for the given partner id.
     *
     * @param partnerId the id of the partner to query upon
     * @return the count of bookings sold to that partner
     */
    public long countBookingsForPartner(Long partnerId) {
        TypedQuery<Long> namedQuery = getEntityManager().createNamedQuery(Booking.COUNT_TOTAL_BOOKINGS_FOR_PARTNER, Long.class).setParameter("pid", partnerId);
        return namedQuery.getSingleResult();
    }

    /**
     * Counts the number of participants for the given trip id.
     *
     * @param tripId the id of the trip to query upon
     * @return the count of participants for that trip
     */
    public long countBookedParticipantsForTrip(Long tripId) {
        TypedQuery<Long> typedQuery = getEntityManager().createNamedQuery(Booking.COUNT_TOTAL_PARTICIPANTS_FOR_TRIP, Long.class).setParameter("tid", tripId);
        return typedQuery.getSingleResult();
    }

    public Booking create(Booking booking){
        Trip merge = getEntityManager().merge(booking.getTrip());
        booking.setTrip(merge);
        return super.create(booking);
    }
}
