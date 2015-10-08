package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.XMLUtils.JAXBLocalDateTimeAdapter;
import com.realdolmen.rdtravel.XMLUtils.JAXBLongAdapter;
import com.realdolmen.rdtravel.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @XmlSchemaType(name = "long")
    @XmlID
    @XmlJavaTypeAdapter(JAXBLongAdapter.class)
    private Long id;

    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @XmlJavaTypeAdapter(JAXBLocalDateTimeAdapter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @XmlJavaTypeAdapter(JAXBLocalDateTimeAdapter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    @Digits(fraction = 2, integer = 8)
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 1)
    private int maxSeats;

    @Column(nullable = false)
    @Min(value = 0)
    private int occupiedSeats;

    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Airport destination;

    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Airport departure;

    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Partner partner;

    @Version
    private long version;

    public Flight() {
    }

    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, BigDecimal price, int maxSeats, int occupiedSeats, Airport destination, Airport departure, Partner partner) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.maxSeats = maxSeats;
        this.occupiedSeats = occupiedSeats;
        this.destination = destination;
        this.departure = departure;
        this.partner = partner;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (maxSeats != flight.maxSeats) return false;
        if (occupiedSeats != flight.occupiedSeats) return false;
        if (id != null ? !id.equals(flight.id) : flight.id != null) return false;
        if (departureTime != null ? !departureTime.equals(flight.departureTime) : flight.departureTime != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(flight.arrivalTime) : flight.arrivalTime != null) return false;
        if (price != null ? !price.equals(flight.price) : flight.price != null) return false;
        if (destination != null ? !destination.equals(flight.destination) : flight.destination != null) return false;
        if (departure != null ? !departure.equals(flight.departure) : flight.departure != null) return false;
        return !(partner != null ? !partner.equals(flight.partner) : flight.partner != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + maxSeats;
        result = 31 * result + occupiedSeats;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (partner != null ? partner.hashCode() : 0);
        return result;
    }
}
