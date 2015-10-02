package com.realdolmen.rdtravel.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by JSTAX29 on 2/10/2015.
 */
public class Flight {
    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private int maxSeats;
    private int occupiedSeats;
    private Airport destination;
    private Airport departure;

    protected Flight(){}

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
}
