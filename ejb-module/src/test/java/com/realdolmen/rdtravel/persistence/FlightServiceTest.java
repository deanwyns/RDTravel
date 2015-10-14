package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Partner;
import com.realdolmen.rdtravel.services.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by JSTAX29 on 14/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest extends DataSetPersistenceTest {
    @Mock
    private FlightDAO mockFlightDAO;

    @InjectMocks
    private FlightService flightService;
    private FlightDAO flightDAO;

    @Before
    public void initialize(){
        flightDAO = new FlightDAO();
        flightDAO.setEntityManager(entityManager());
        Mockito.when(mockFlightDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(mockFlightDAO.update(Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void testCreateRegularFlight(){
        Airport destination = entityManager().find(Airport.class, 1l);
        Airport departure = entityManager().find(Airport.class, 2l);
        Partner partner = entityManager().find(Partner.class, 1l);
        Flight flight = new Flight(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), BigDecimal.valueOf(12.5), 12, 0, destination, departure, partner);
        flightService.updateFlight(flight);
        flushAndClear();

        //2 flights in data.xml. 1 added. There should be 3.
        assertEquals(3, flightDAO.findAll().size());
    }

    @Test
    public void testUpdateFlight(){
        Flight flightToBeUpdated = flightDAO.read(1l);
        flightToBeUpdated.setOccupiedSeats(flightToBeUpdated.getMaxSeats());
        flightService.updateFlight(flightToBeUpdated);
        flushAndClear();

        Flight updatedFlight = flightDAO.read(1l);
        assertEquals(flightToBeUpdated.getOccupiedSeats(), updatedFlight.getOccupiedSeats());
        assertEquals(2, flightDAO.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDepartureBeforeToday(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDepartureTime(LocalDateTime.now().minusDays(1));
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDepartureAfterArrival(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDepartureTime(flightToPersist.getArrivalTime().plusDays(1));
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDepartureTimeEqualToArrivalTime(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDepartureTime(flightToPersist.getArrivalTime());
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDiscountButNoPercentage(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDiscount(true);
        flightToPersist.setDiscountPercentage(null);
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDiscountButNoSeatThreshold(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDiscount(true);
        flightToPersist.setSeatsThresholdForDiscount(null);
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFlightDepartureEqualToArrival(){
        Flight flightToPersist = flightDAO.read(1l);
        flightToPersist.setDeparture(flightToPersist.getDestination());
        flightService.updateFlight(flightToPersist);
        flushAndClear();
    }
}
