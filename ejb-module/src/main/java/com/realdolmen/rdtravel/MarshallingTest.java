package com.realdolmen.rdtravel;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.domain.Partner;
import com.realdolmen.rdtravel.domain.Trip;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class MarshallingTest {
    public static void main(String[] args) {
        createXML();
//        readXML();
    }

    private static void readXML() {
        try {
            File file = new File("trip.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Trip.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Trip trip = (Trip) jaxbUnmarshaller.unmarshal(file);
            System.out.println(trip);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void createXML() {
        Trip trip = new Trip();

        Flight flight = new Flight();
        flight.setDeparture(new Airport("Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.461389, 83, 0, 'E', "Europe/London"));
        flight.setDestination(new Airport("Pokhara", "Pokhara", "Nepal", "PKR", "VNPK", 28.200881, 83.982056, 2712, 5.75, 'N', "Asia/Katmandu"));
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

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Trip.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(trip, new File("trip.xml"));
            jaxbMarshaller.marshal(trip, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
