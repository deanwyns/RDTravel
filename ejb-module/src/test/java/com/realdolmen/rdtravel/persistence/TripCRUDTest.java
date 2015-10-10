package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.*;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by JSTAX29 on 7/10/2015.
 */
public class TripCRUDTest extends DataSetPersistenceTest {
    private TripDAO tripDAO = new TripDAO();
    private Trip trip;

    @Before
    public void initialize() {
        tripDAO.setEntityManager(entityManager());
        trip = tripDAO.read(1l);
        flushAndClear();
    }

    @Test
    public void testCreateCompleteTrip() {
        Flight flight = entityManager().find(Flight.class, 1l);
        Trip newTrip = new Trip("Super Fun Trip", BigDecimal.valueOf(23.5), LocalDate.now(), LocalDate.now(), new ArrayList<>(Arrays.asList(flight, flight)));
        assertNull(newTrip.getId());
        tripDAO.create(newTrip);
        flushAndClear();
        assertNotNull(newTrip.getId());
    }

    @Test
    public void testUpdateTrip() {
        String revisionedName = "This trip just became cooler.";
        trip.setName(revisionedName);
        tripDAO.update(trip);
        flushAndClear();
        Trip changedTrip = tripDAO.read(1l);
        assertEquals(revisionedName, changedTrip.getName());
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTripNameTooLong() {
        trip.setName("This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name.");
        tripDAO.update(trip);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTripNameTooShort() {
        trip.setName("");
        tripDAO.update(trip);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPricePerDayNegative() {
        trip.setPricePerDay(BigDecimal.valueOf(-5));
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testPricePerDayTooManyFractions() {
        trip.setPricePerDay(BigDecimal.valueOf(12.512));
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testPricePerDayIsNull() {
        trip.setPricePerDay(null);
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testStartDateIsNull() {
        trip.setStartDate(null);
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testEndDateIsNull() {
        trip.setEndDate(null);
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testFlightsIsNull() {
        trip.setFlights(null);
        tripDAO.update(trip);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTripWithInvalidFlights() {
        Flight f = entityManager().find(Flight.class, 1l);
        f.setMaxSeats(-5);
        List<Flight> flights = new ArrayList<>(Collections.singletonList(f));
        trip.setFlights(flights);
        tripDAO.update(trip);
        flushAndClear();
    }


    @Test(expected = ConstraintViolationException.class)
    public void testFlightListIsEmpty() {
        List<Flight> flights = new ArrayList<>();
        trip.setFlights(flights);
        tripDAO.update(trip);
        flushAndClear();
    }

    @Test(expected = OptimisticLockException.class)
    public void testConcurrentTripWithOutdatedFlight() {
        //Trip is received in the before method. Flush and clear to close the current transaction
        flushAndClear();
        //Update the flight that is linked to the trip so it no longer has enough available seats
        Flight flight = entityManager().find(Flight.class, 1l);
        flight.setOccupiedSeats(flight.getMaxSeats() - 2);
        flushAndClear();
        //When persisting the trip, it should no longer be a valid trip due to outdated information
        entityManager().merge(trip);
    }

    @Test
    public void testFindAllTripsWithFinalAirportNull(){
        assertEquals(0, tripDAO.findByDestinationCountry(null).size());
    }

    @Test
    public void testFindAllTripsWithFinalAirport(){
        Airport airport2 = entityManager().find(Airport.class, 2l);
        assertEquals(2, tripDAO.findByDestinationAirport(airport2).size());
    }

    @Test
    public void testFindAllTripsWithFinalCountryNull(){
        assertEquals(0, tripDAO.findByDestinationAirport(null).size());
    }

    @Test
    public void testFindAllTripsWithFinalCountry(){
        Country country1 = entityManager().find(Country.class, 1);
        assertEquals(2, tripDAO.findByDestinationCountry(country1).size());
    }
}
