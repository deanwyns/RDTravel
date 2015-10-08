package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JSTAX29 on 7/10/2015.
 */
public class TripCRUDTest extends DataSetPersistenceTest {
    private TripDAO tripDAO = new TripDAO();
    private Trip trip;

    @Before
    public void initialize(){
        tripDAO.setEntityManager(entityManager());
        trip = tripDAO.read(1l);
    }

    @Test
    public void testCreateCompleteTrip(){
        Flight flight = entityManager().find(Flight.class, 1l);
        Trip newTrip = new Trip("Super Fun Trip", BigDecimal.valueOf(23.5), LocalDate.now(), LocalDate.now(), new ArrayList<>(Arrays.asList(flight, flight)));
        assertNull(newTrip.getId());
        tripDAO.create(newTrip);
        flushAndClear();
        assertNotNull(newTrip.getId());
    }

    @Test
    public void testUpdateTrip(){
        String revisionedName = "This trip just became cooler.";
        trip.setName(revisionedName);
        flushAndClear();
        Trip changedTrip = tripDAO.read(1l);
        assertEquals(revisionedName, changedTrip.getName());
    }

    @Test
    public void testDeleteTrip(){
        tripDAO.delete(trip);
        flushAndClear();
        Trip oldTrip = tripDAO.read(1l);
        assertNull(oldTrip);
    }
}
