package com.realdolmen;

import com.realdolmen.rdtravel.XMLUtils.JAXBWrapper;
import com.realdolmen.rdtravel.XMLUtils.MarshallerUtil;
import com.realdolmen.rdtravel.domain.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class MarshallingTest {
    public static void main(String[] args) {
        createXML();
//        readXML();
    }

    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static void readXML() {
        try {
            String fileContentAsString = readFile("trips.xml", Charset.defaultCharset());

            JAXBContext jaxbContext = JAXBContext.newInstance(JAXBWrapper.class, Trip.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(new StringReader(fileContentAsString));

            List<Trip> trips = MarshallerUtil.unmarshal(jaxbUnmarshaller, Trip.class, xmlStreamReader);
            System.out.println(Arrays.toString(trips.toArray()));

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createXML() {
        Trip trip = new Trip();

        Flight flight = new Flight();
        flight.setDeparture(new Airport("Heathrow", "London", new Country(), "LHR", "EGLL", 51.4775, -0.461389, 83, (byte) 0, 'E', "Europe/London"));
        flight.setDestination(new Airport("Pokhara", "Pokhara", new Country(), "PKR", "VNPK", 28.200881, 83.982056, 2712, (byte) 5, 'N', "Asia/Katmandu"));
        flight.setDepartureTime(LocalDateTime.now());
        flight.setArrivalTime(LocalDateTime.now());
        flight.setPrice(BigDecimal.valueOf(51.25));
        flight.setMaxSeats(50);
        flight.setOccupiedSeats(12);
        flight.setPartner(new Partner("JetAir Fly"));


        trip.setName("Example trip");
        trip.setEndDate(LocalDate.now());
        trip.setStartDate(LocalDate.now());
        trip.setPricePerDay(BigDecimal.valueOf(20.5));
        trip.setFlights(Arrays.asList(
                flight,
                flight
        ));


        List<Trip> tripList = new ArrayList<>(Arrays.asList(trip, trip, trip));

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(JAXBWrapper.class, Trip.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

//            jaxbMarshaller.marshal(tripList, new File("trips.xml"));
            MarshallerUtil.marshal(jaxbMarshaller, tripList, "trips", new File("trips.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}