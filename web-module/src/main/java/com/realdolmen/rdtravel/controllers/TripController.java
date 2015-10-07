package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.XMLUtils.JAXBWrapper;
import com.realdolmen.rdtravel.XMLUtils.MarshallerUtil;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.FlightDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;
import org.jdom2.*;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Named
@RequestScoped
public class TripController {

    @Inject
    private TripDAO tripDAO;
    @Inject
    private FlightDAO flightDAO;

    /**
     * When uploading an XML file, the tripcontroller will parse the xml and import it to the database.
     * This method is called from the fileUploadView to redirect the uploading logic to the parsing and persistence logic.
     *
     * @param fileContent the content that was found inside the given file.
     */
    public void parseAndPersistTrip(byte[] fileContent) {

        try {
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
//            for (int i = 0; i < 1; i++) {
                Trip trip = tripList.get(i);
                List<Long> flightIdList = getIdsFromXml(fileContentAsString, (i + 1));

                //Find the flights in the database
                List<Flight> flightList = flightDAO.findAllWithIds(flightIdList);

                //There were more ids than there are found flights. Some flights were not found.
                if (flightIdList.size() != flightList.size())
                    throw new IllegalArgumentException("Not all flights were found in the database. The trip has not been added.");

                //Connect the flights to the trip
                trip.setFlights(flightList);

                //If the ID of the trip is not null, it's editing an existing trip. Update it.
                if (trip.getId() != null) {
                    tripDAO.update(trip);

                    //Let the user know updating was successful
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload successful", "File was uploaded and trips have been updated.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    //No Trip id present, it's a new trip. Persist the trip to the database
                    tripDAO.create(trip);

                    //Let the user know creating was successful
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload successful", "File was uploaded and trips have been created.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }


        } catch (JAXBException | UnsupportedEncodingException | NumberFormatException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parse failed", "File could not be parsed as a trip.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (PersistenceException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", "The trip could not be saved to the database.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
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


}
