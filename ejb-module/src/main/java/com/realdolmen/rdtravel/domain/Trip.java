package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.util.JAXBLocalDateAdapter;
import com.realdolmen.rdtravel.util.LocalDatePersistenceConverter;

import javax.persistence.*;
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
    private String name;

    @XmlElement(required = true)
    private BigDecimal pricePerDay;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JAXBLocalDateAdapter.class)
    private LocalDate startDate;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JAXBLocalDateAdapter.class)
    private LocalDate endDate;

    @ManyToMany
    @XmlIDREF
    @XmlSchemaType(name = "long")
    @XmlElement(name = "flightId")
    @XmlElementWrapper
    private List<Flight> flights;

    public Trip() {
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trip{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(pricePerDay);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", flights=").append(flights);
        sb.append('}');
        return sb.toString();
    }
}
