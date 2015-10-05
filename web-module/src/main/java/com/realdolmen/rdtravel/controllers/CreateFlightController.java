package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Airport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@RequestScoped
public class CreateFlightController implements Serializable {
    private CreateFlightViewModel viewModel = this.new CreateFlightViewModel();

    public String create() {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println(viewModel.departureAirport.toString());
        System.out.println(viewModel.destinationAirport.toString());
        return "/overview.xhtml";
    }

    public CreateFlightViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(CreateFlightViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public class CreateFlightViewModel {
        private Airport departureAirport;
        private Airport destinationAirport;
        private Integer availableSeats;
        private BigDecimal price;

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
    }
}