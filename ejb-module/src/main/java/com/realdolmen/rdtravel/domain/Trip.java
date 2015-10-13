package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.XMLUtils.JAXBLocalDateAdapter;
import com.realdolmen.rdtravel.util.LocalDatePersistenceConverter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by JSTAX29 on 2/10/2015.
 * The trip that customers can book.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @XmlElement(required = true)
    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    @Digits(fraction = 2, integer = 8)
    private BigDecimal pricePerDay;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JAXBLocalDateAdapter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JAXBLocalDateAdapter.class)
    @NotNull
    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @XmlIDREF
    @XmlSchemaType(name = "long")
    @XmlElement(name = "flightId")
    @XmlElementWrapper
    @NotNull
    @Size(min = 1)
    @Valid
    private List<Flight> flights;

    @XmlElement(required = false)
    @Version
    private long version;

    public Trip() {
    }

    public Trip(String name, BigDecimal pricePerDay, LocalDate startDate, LocalDate endDate, List<Flight> flights) {
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal price) {
        this.pricePerDay = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Flight getLastFlight() {
        if(getFlights().size() < 0)
            return null;

        return getFlights().get(getFlights().size() - 1);
    }

    @Override
    public String toString() {
        String toString = "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", flights={";


        for (Flight f : flights) {
            toString += f;
        }

        toString += "}}";

        return toString;
    }
}
