package com.realdolmen.rdtravel.persistence;

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
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        Mockito.when(mockFlightDAO.findAllWithIds(new ArrayList<>(Arrays.asList((long) 1, (long) 2)))).thenReturn(new ArrayList<>(Arrays.asList(flight1, flight2)));
        Mockito.when(mockFlightDAO.findAllWithIds(new ArrayList<>(Collections.singletonList((long) 1)))).thenReturn(new ArrayList<>(Collections.singletonList(flight1)));
        Mockito.when(mockFlightDAO.findAllWithIds(new ArrayList<>(Collections.singletonList((long) 2)))).thenReturn(new ArrayList<>(Collections.singletonList(flight2)));

        Mockito.when(mockTripDAO.getEntityManager()).thenReturn(entityManager());
        Mockito.when(mockTripDAO.create(Mockito.any())).thenCallRealMethod();
        Mockito.when(mockTripDAO.update(Mockito.any())).thenCallRealMethod();

        tripDAO.setEntityManager(entityManager());
    }


    @Test
    public void testImportValidTrips() throws IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, URISyntaxException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/valid_trips.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. Three are being added. So there should be 5.
        assertEquals(5, tripDAO.findAll().size());
    }

    @Test
    public void testImportTripsUpdatesOtherTrips() throws URISyntaxException, IOException, JDOMException, XMLStreamException, FlightNotFoundException, JAXBException, FlightOutsideTripDateException {
        java.net.URL url = this.getClass().getResource("/testing_trips/valid_trips_updating.xml");
        importExportTripService.parseAndPersistTrip(Files.readAllBytes(Paths.get(url.toURI())));

        //Two are currently in the data.xml. Two are updated, one is added. There should be 3.
        assertEquals(3, tripDAO.findAll().size());
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

}
