package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void testDeleteTrip() {
        tripDAO.delete(trip);
        flushAndClear();
        Trip oldTrip = tripDAO.read(1l);
        flushAndClear();
        assertNull(oldTrip);
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


}