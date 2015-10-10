package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Country;
import com.realdolmen.rdtravel.domain.Trip;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }

    /**
     * Find all trips with the given country as the last destination (the last flight's destination).
     * @param country
     * @return
     */
    public List<Trip> findByDestinationCountry(Country country) {
        List<Trip> trips = this.findAll();

        return trips.stream().filter(t -> {
                    int amount = t.getFlights().size();
                    return amount != 0 && t.getFlights().get(amount - 1).getDestination().getCountry().equals(country);
                }
        ).collect(Collectors.toList());
    }

    /**
     * Find all trips with the given airport as the last destination (the last flight's destination).
     * @param airport
     * @return
     */
    public List<Trip> findByDestinationAirport(Airport airport) {
        List<Trip> trips = this.findAll();

        return trips.stream().filter(t -> {
                    int amount = t.getFlights().size();
                    return amount != 0 && t.getFlights().get(amount - 1).getDestination().equals(airport);
                }
        ).collect(Collectors.toList());
    }
}
