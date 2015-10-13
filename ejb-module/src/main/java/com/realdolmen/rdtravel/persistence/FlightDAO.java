package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Partner;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DWSAX40 on 5/10/2015.
 * An implementation of the generic DAO for flights.
 */
@Named
@RequestScoped
public class FlightDAO extends GenericDaoImpl<Flight, Long> {

    @PersistenceContext
    private EntityManager em;

    public FlightDAO() {
        super(Flight.class);
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
     * Finds all flights that have the id's from the parameter id list.
     * If the list is empty or null, an empty list of flights will be returned.
     * @param ids the list of ids to find flights
     * @return the list of flights that have the ids
     * @throws
     */
    public List<Flight> findAllWithIds(List<Long> ids) {
        if(ids != null && !ids.isEmpty()){
            TypedQuery<Flight> flightTypedQuery = getEntityManager()
                    .createNamedQuery(Flight.FIND_ALL_WITH_IDS, Flight.class)
                    .setParameter("idList", ids);
            return flightTypedQuery.getResultList();
        }
        return new ArrayList<>();
    }

    public List<Flight> findByPartner(Partner partner) {
        return em.createNamedQuery(Flight.FIND_BY_PARTNER, Flight.class).setParameter("partner", partner).getResultList();
    }

}
