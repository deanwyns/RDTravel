package com.realdolmen.rdtravel.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Entity
public class Partner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 255)
    private String name;
    @Min(1)
    private Integer ticketThresholdForDiscount;
    @Min(0)
    @Max(100)
    private BigDecimal discountPercentage;

    @Version
    private long version;

    public Partner() {
    }

    public Partner(String name) {
        this.name = name;
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

    public Integer getTicketThresholdForDiscount() {
        return ticketThresholdForDiscount;
    }

    public void setTicketThresholdForDiscount(Integer ticketThresholdForDiscount) {
        this.ticketThresholdForDiscount = ticketThresholdForDiscount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partner partner = (Partner) o;

        if (version != partner.version) return false;
        if (id != null ? !id.equals(partner.id) : partner.id != null) return false;
        return !(name != null ? !name.equals(partner.name) : partner.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (version ^ (version >>> 32));
        return result;
    }
}
