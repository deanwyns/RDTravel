package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.XMLUtils.JAXBLocalDateTimeAdapter;
import com.realdolmen.rdtravel.XMLUtils.JAXBLongAdapter;
import com.realdolmen.rdtravel.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by JSTAX29 on 2/10/2015.
 * A flight which is used in a trip.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = Flight.FIND_ALL_WITH_IDS, query = "select f from Flight f where f.id in :idList")
})
public class Flight {
    public static final String FIND_ALL_WITH_IDS = "Flight.findAllWithIds";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlSchemaType(name="long")
    @XmlID
    @XmlJavaTypeAdapter(JAXBLongAdapter.class)
    private Long id;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @XmlJavaTypeAdapter(JAXBLocalDateTimeAdapter.class)
    private LocalDateTime departureTime;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @XmlJavaTypeAdapter(JAXBLocalDateTimeAdapter.class)
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private int maxSeats;
    private int occupiedSeats;
    @ManyToOne
    private Airport destination;
    @ManyToOne
    private Airport departure;
    @ManyToOne
    private Partner partner;

    @Version
    private long version;

    public Flight(){}

    public Long getId() {
        return id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Flight{");
        sb.append("id=").append(id);
        sb.append(", departureTime=").append(departureTime);
        sb.append(", arrivalTime=").append(arrivalTime);
        sb.append(", price=").append(price);
        sb.append(", maxSeats=").append(maxSeats);
        sb.append(", occupiedSeats=").append(occupiedSeats);
        sb.append(", destination=").append(destination);
        sb.append(", departure=").append(departure);
        sb.append('}');
        return sb.toString();
    }
}
