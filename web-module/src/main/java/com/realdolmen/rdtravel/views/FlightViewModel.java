package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Airport;

import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@RequestScoped
public class FlightViewModel {
    @NotNull private Airport departureAirport;
    @NotNull private Airport destinationAirport;
    @NotNull
    @Min(value = 1)
    private Integer availableSeats;
    @NotNull
    @Min(value = 1)
    private BigDecimal price;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
