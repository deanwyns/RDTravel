package com.realdolmen.rdtravel.services;

import com.realdolmen.rdtravel.XMLUtils.JAXBWrapper;
import com.realdolmen.rdtravel.XMLUtils.MarshallerUtil;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.exceptions.FlightNotFoundException;
import com.realdolmen.rdtravel.exceptions.FlightOutsideTripDateException;
import com.realdolmen.rdtravel.persistence.FlightDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSTAX29 on 7/10/2015.
 */
@Named
@RequestScoped
public class ImportExportTripService {
    @Inject
    private TripDAO tripDAO;
    @Inject
    private FlightDAO flightDAO;

    /**
     * When uploading an XML file, the importTripService will parse the xml and import it to the database.
     * This method is called from the tripController to redirect the uploading logic to the parsing and persistence logic.
     * If there is a wrongly formatted trip in the file, no trips will be added. Every trip during persisting will be rollbacked.
     *
     * @param fileContent the content that was found inside the given file
     */
    @Transactional
    public void parseAndPersistTrip(byte[] fileContent) throws IOException, JAXBException, XMLStreamException, JDOMException, FlightNotFoundException, FlightOutsideTripDateException {

        //Convert the byte[] to a String
        String fileContentAsString = new String(fileContent, "UTF-8");

        //Let JAXB create a Trip class from the XML, without flights since they are entered by ID
        JAXBContext jaxbContext = JAXBContext.newInstance(JAXBWrapper.class, Trip.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(new StringReader(fileContentAsString));
        List<Trip> tripList = MarshallerUtil.unmarshal(jaxbUnmarshaller, Trip.class, xmlStreamReader);
        //Find the flight ID's from the XML that could not be processed by JAXB

        for (int i = 0; i < tripList.size(); i++) {
            Trip trip = tripList.get(i);
            List<Long> flightIdList = getIdsFromXml(fileContentAsString, (i + 1));

            //Find the flights in the database
            List<Flight> flightList = flightDAO.findAllWithIds(flightIdList);

            //There were more ids than there are found flights. Some flights were not found.
            if (flightIdList.size() != flightList.size())
                throw new FlightNotFoundException("Not all flights were found in the database. The trip has not been added.");

            for (Flight flight : flightList) {
                if ((trip.getStartDate() != null && trip.getEndDate() != null) &&
                        (flight.getDepartureTime().isBefore(trip.getStartDate().atStartOfDay()) ||
                                flight.getArrivalTime().isAfter(trip.getEndDate().atStartOfDay())))
                    throw new FlightOutsideTripDateException("Flight " + flight.getId() + " was not within the start date and end date for trip " + trip.getName());
            }

            //Connect the flights to the trip
            trip.setFlights(flightList);

            //If the ID of the trip is not null, it's editing an existing trip. Update it.
            if (trip.getId() != null) {
                tripDAO.update(trip);
            } else {
                //No Trip id present, it's a new trip. Persist the trip to the database
                tripDAO.create(trip);
            }
        }


    }

    /**
     * Finds the ids that are within the XML file and returns them as a list of Long values
     *
     * @param fileContentAsString the xml as a string
     * @param tripId              the id of the currently iterated trip.
     * @return the list of ids as Long values
     * @throws JDOMException
     * @throws IOException
     */
    private List<Long> getIdsFromXml(String fileContentAsString, int tripId) throws JDOMException, IOException {
        XPathFactory xpf = XPathFactory.instance();
        XPathExpression<Element> xpath = xpf.compile("//trip[" + tripId + "]/flights/flightId", Filters.element());
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new StringReader(fileContentAsString));
        List<Element> idElementList = xpath.evaluate(document);

        List<Long> flightIdList = new ArrayList<>();

        for (Element idElement : idElementList) {
            flightIdList.add(Long.parseLong(idElement.getText()));
        }
        return flightIdList;
    }

    /**
     * Exports all trips of the database to an XML file.
     */
    public void exportTripsToXmlFile() throws JAXBException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(JAXBWrapper.class, Trip.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        MarshallerUtil.marshal(jaxbMarshaller, tripDAO.findAll(), "trips", new File("file.xml"));
    }


}
