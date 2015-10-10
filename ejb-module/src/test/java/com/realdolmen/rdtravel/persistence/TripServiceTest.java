package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.services.TripService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by JSTAX29 on 10/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest extends DataSetPersistenceTest {

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    private TripService tripService;

    @Before
    public void initialize() {
        Airport airport = entityManager().find(Airport.class, 2l);

        Mockito.when(tripDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(tripDAO.findByDestinationAirport(airport)).thenCallRealMethod();

    }


    @Test
    public void testFindTripForGivenAirportWithinCorrectDates() {
        /*
        * <trip id="1" name="Super fun trip" pricePerDay="25.20" startDate="2999-10-04" endDate="2999-10-07" version="0"/>
        * <trip_flight Trip_id="1" flights_id="1"/>
        * <trip_flight Trip_id="1" flights_id="2"/>
        * <flight id='2' departureTime='2999-10-06 00:00:00' arrivalTime='2999-10-07 00:00:00' maxSeats='50' occupiedSeats='0'
        *    price='100.90' version='0' departure_id='3' destination_id='2' partner_id="2" discount="0"/>
        * <airport id="2" altitude="20" city="Madang" daylightSavingsTime="U" iataFaa="MAG" icao="AYMD" latitude="-5.207083"
        * longitude="145.7887" name="Madang" timezone="10" timezoneTzFormat="Pacific/Port_Moresby" country_id="1"
        * version="0"/>
        *
        * Trip 1 has 2 flights. Flight 2 is the last flight of the trip and goes to airport 2.
        * When searching for a trip to airport 2, with a departure before the trip and return after the trip, one trip should be found.
        * */
        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.of(2999, 10, 2);
        LocalDate returnPick = LocalDate.of(2999, 10, 8);

        assertEquals(1, tripService.findBy(departurePick, returnPick, airport, 1).size());
    }


    @Test
    public void testFindTripForGivenAirportPreferredDepartureIsAfterTrip() {
        //Should return no trips
        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.of(2999, 10, 5);
        LocalDate returnPick = LocalDate.of(2999, 10, 8);

        assertEquals(0, tripService.findBy(departurePick, returnPick, airport, 1).size());
    }

    @Test
    public void testFindTripForGivenAirportPreferredReturnIsBeforeTrip() {
        //Should return no trips
        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.of(2999, 10, 2);
        LocalDate returnPick = LocalDate.of(2999, 10, 6);

        assertEquals(0, tripService.findBy(departurePick, returnPick, airport, 1).size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testFindTripDepartureDatePassed() {
        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.now().minusDays(1);
        LocalDate returnPick = LocalDate.of(2999, 10, 8);

        tripService.findBy(departurePick, returnPick, airport, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTripDepartureIsAfterReturn() {
        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.of(2999, 10, 9);
        LocalDate returnPick = LocalDate.of(2999, 10, 8);

        tripService.findBy(departurePick, returnPick, airport, 1);
    }

    @Test
    public void testFindTripsNoParticipantSpaceAvailable() {
        Flight flight = entityManager().find(Flight.class, 2l);
        flight.setOccupiedSeats(flight.getMaxSeats() - 1);
        flushAndClear();

        Airport airport = entityManager().find(Airport.class, 2l);

        LocalDate departurePick = LocalDate.of(2999, 10, 2);
        LocalDate returnPick = LocalDate.of(2999, 10, 8);

        assertEquals(0, tripService.findBy(departurePick, returnPick, airport, 1).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxSeatsForTripNoFlightsAvailable() {
        //The trip can't have empty flights due to the annotation.
        Trip trip = new Trip();
        trip.setFlights(new ArrayList<>());

        tripService.getMaxSeatsForTrip(trip);
    }

    @Test
    public void testMaxSeatsForTripReturnsSmallestMaxSeats() {
        Assert.fail();
        //Commentaar: Get the least available seats of all flights
        //Methode: getMaxSeatsForTrip
        //Wat?
    }
}
