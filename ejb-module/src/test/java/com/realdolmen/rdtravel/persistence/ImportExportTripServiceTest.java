package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.exceptions.FlightNotFoundException;
import com.realdolmen.rdtravel.exceptions.FlightOutsideTripDateException;
import com.realdolmen.rdtravel.services.ImportExportTripService;
import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImportExportTripServiceTest extends DataSetPersistenceTest {

    private TripDAO tripDAO = new TripDAO();
    @Mock
    private TripDAO mockTripDAO;
    @Mock
    private FlightDAO mockFlightDAO;
    @InjectMocks
    private ImportExportTripService importExportTripService;

    @Before
    public void initialize() {
        Flight flight1 = entityManager().find(Flight.class, 1l);
        Flight flight2 = entityManager().find(Flight.class, 2l);
        when(mockFlightDAO.findAllWithIds(new ArrayList<>(Arrays.asList((long) 1, (long) 2)))).thenReturn(new ArrayList<>(Arrays.asList(flight1, flight2)));
        when(mockFlightDAO.findAllWithIds(new ArrayList<>(Collections.singletonList((long) 1)))).thenReturn(new ArrayList<>(Collections.singletonList(flight1)));
        when(mockFlightDAO.findAllWithIds(new ArrayList<>(Collections.singletonList((long) 2)))).thenReturn(new ArrayList<>(Collections.singletonList(flight2)));

        when(mockTripDAO.getEntityManager()).thenReturn(entityManager());
        when(mockTripDAO.create(any())).thenCallRealMethod();
        when(mockTripDAO.update(any())).thenCallRealMethod();

        tripDAO.setEntityManager(entityManager());
    }


    @Test
    public void testImportValidTrips() throws IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, URISyntaxException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/valid_trips.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Three are currently in the data.xml. Three are being added. So there should be 6.
        assertEquals(6, tripDAO.findAll().size());
    }

    @Test
    public void testImportTripsUpdatesOtherTrips() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/valid_trips_updating.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Three are currently in the data.xml. Two are updated, one is added. There should be 4.
        assertEquals(4, tripDAO.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTripIsOnlyOneDay() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightOutsideTripDateException, FlightNotFoundException, JAXBException {
        java.net.URL url = this.getClass().getResource("/testing_trips/invalid_trip_dates.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = FlightNotFoundException.class)
    public void testImportNoFlightFound() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/flight_not_found_flight.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testImportInvalidCharacters() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/invalid_characters_trips.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testInvalidFormat() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/invalid_formatted_trips.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = FlightOutsideTripDateException.class)
    public void testFlightsAreWithinTripTime() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/flights_outside_trip_dates.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTripDepartureAfterReturn() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/trip_departure_after_return.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTripDeparturePassed() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/trip_departure_passed.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. All added trips should be rollbacked due to the error.
        assertEquals(2, tripDAO.findAll().size());
    }

    @Test
    public void testExportingTripsGivesFile() throws MalformedURLException, JAXBException, URISyntaxException {
        File file = importExportTripService.exportTripsToXmlFile();
        assertNotNull(file);
    }
}
