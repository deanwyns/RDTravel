package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Partner;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;


/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class FlightCRUDTest extends DataSetPersistenceTest {
    private FlightDAO flightDAO = new FlightDAO();
    private Flight flight;

    @Before
    public void initialize() {
        flightDAO.setEntityManager(entityManager());
        flight = flightDAO.read(1l);
        flushAndClear();
    }

    @Test
    public void testCreateFlight() {
        Airport departure = entityManager().find(Airport.class, 1l);
        Airport arrival = entityManager().find(Airport.class, 2l);
        Partner partner = entityManager().find(Partner.class, 1l);
        Flight flight = new Flight(LocalDateTime.now(), LocalDateTime.now().plusDays(5), BigDecimal.valueOf(12.5), 10, 20, arrival, departure, partner);
        assertNull(flight.getId());
        flightDAO.create(flight);
        flushAndClear();
        assertNotNull(flight.getId());
    }

    @Test
    public void testUpdateFlight() {
        int revisionedvalue = 100;
        flight.setMaxSeats(revisionedvalue);
        flightDAO.update(flight);
        flushAndClear();
        Flight changedFlight = flightDAO.read(1l);
        assertEquals(revisionedvalue, changedFlight.getMaxSeats());
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testDepartureTimeNull() {
        flight.setDepartureTime(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testArrivalTimeNull() {
        flight.setArrivalTime(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPriceIsNull() {
        flight.setPrice(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPriceIsNegative() {
        flight.setPrice(BigDecimal.valueOf(-5));
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPriceHasMoreThan2Fractions() {
        flight.setPrice(BigDecimal.valueOf(5.123));
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPriceIsMassive() {
        flight.setPrice(BigDecimal.valueOf(999999999999999.99));
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testMaxSeatsIs0() {
        flight.setMaxSeats(0);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testMaxSeatsIsNegative() {
        flight.setMaxSeats(-5);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testOccupiedSeatsIsNegative() {
        flight.setOccupiedSeats(-5);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testDestinationIsNull() {
        flight.setDestination(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testDepartureIsNull() {
        flight.setDeparture(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPartnerIsNull() {
        flight.setPartner(null);
        flightDAO.update(flight);
        flushAndClear();
    }

    @Test
    public void testFindAllFlightsWithIds(){
        Flight flightWithId1 = flightDAO.read(1l);
        Flight flightWithId2 = flightDAO.read(2l);
        List<Flight> correctFlightList = new ArrayList<>(Arrays.asList(flightWithId1, flightWithId2));
        flushAndClear();

        List<Long> idList = new ArrayList<>(Arrays.asList((long) 1, (long) 2));
        List<Flight> foundFlights = flightDAO.findAllWithIds(idList);

        assertEquals(correctFlightList, foundFlights);
    }
}
