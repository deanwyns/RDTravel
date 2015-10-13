package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.XMLUtils.JAXBLongAdapter;
import com.realdolmen.rdtravel.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by JSTAX29 on 2/10/2015.
 * A flight which is used in a trip.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = Flight.FIND_ALL_WITH_IDS, query = "select f from Flight f where f.id in :idList"),
        @NamedQuery(name = Flight.FIND_BY_PARTNER, query = "SELECT f FROM Flight f WHERE f.partner = :partner")
})
public class Flight {
    public static final String FIND_ALL_WITH_IDS = "Flight.findAllWithIds";
    public static final String FIND_BY_PARTNER = "Flight.findByPartner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlSchemaType(name = "long")
    @XmlID
    @XmlJavaTypeAdapter(JAXBLongAdapter.class)
    private Long id;

    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    @Digits(fraction = 2, integer = 8)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1)
    private Integer maxSeats;

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

    private boolean discount;
    @Min(0)
    private Integer seatsThresholdForDiscount;
    @Min(0)
    @Max(100)
    private BigDecimal discountPercentage;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    @Digits(fraction = 2, integer = 8)
    private BigDecimal extraEndUserCost;

    @Version
    private long version;

    public Flight() {
    }

    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, BigDecimal price, int maxSeats, int occupiedSeats, Airport destination, Airport departure, Partner partner) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        setExtraEndUserCostByBasePrice(price);
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
        setExtraEndUserCostByBasePrice(price);
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
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

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public Integer getSeatsThresholdForDiscount() {
        return seatsThresholdForDiscount;
    }

    public void setSeatsThresholdForDiscount(Integer seatsThresholdForDiscount) {
        this.seatsThresholdForDiscount = seatsThresholdForDiscount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setOccupiedSeats(Integer occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public BigDecimal getExtraEndUserCost() {
        return extraEndUserCost;
    }

    public void setExtraEndUserCost(BigDecimal extraEndUserCost) {
        this.extraEndUserCost = extraEndUserCost;
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

        if (occupiedSeats != flight.occupiedSeats) return false;
        if (discount != flight.discount) return false;
        if (version != flight.version) return false;
        if (id != null ? !id.equals(flight.id) : flight.id != null) return false;
        if (departureTime != null ? !departureTime.equals(flight.departureTime) : flight.departureTime != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(flight.arrivalTime) : flight.arrivalTime != null) return false;
        if (price != null ? !price.equals(flight.price) : flight.price != null) return false;
        if (maxSeats != null ? !maxSeats.equals(flight.maxSeats) : flight.maxSeats != null) return false;
        if (destination != null ? !destination.equals(flight.destination) : flight.destination != null) return false;
        if (departure != null ? !departure.equals(flight.departure) : flight.departure != null) return false;
        if (partner != null ? !partner.equals(flight.partner) : flight.partner != null) return false;
        if (seatsThresholdForDiscount != null ? !seatsThresholdForDiscount.equals(flight.seatsThresholdForDiscount) : flight.seatsThresholdForDiscount != null)
            return false;
        if (discountPercentage != null ? !discountPercentage.equals(flight.discountPercentage) : flight.discountPercentage != null)
            return false;
        return !(extraEndUserCost != null ? !extraEndUserCost.equals(flight.extraEndUserCost) : flight.extraEndUserCost != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (maxSeats != null ? maxSeats.hashCode() : 0);
        result = 31 * result + occupiedSeats;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (partner != null ? partner.hashCode() : 0);
        result = 31 * result + (discount ? 1 : 0);
        result = 31 * result + (seatsThresholdForDiscount != null ? seatsThresholdForDiscount.hashCode() : 0);
        result = 31 * result + (discountPercentage != null ? discountPercentage.hashCode() : 0);
        result = 31 * result + (extraEndUserCost != null ? extraEndUserCost.hashCode() : 0);
        result = 31 * result + (int) (version ^ (version >>> 32));
        return result;
    }

    private void setExtraEndUserCostByBasePrice(BigDecimal basePrice) {
        if(basePrice != null)
            this.extraEndUserCost = basePrice.multiply(BigDecimal.valueOf(0.05)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
