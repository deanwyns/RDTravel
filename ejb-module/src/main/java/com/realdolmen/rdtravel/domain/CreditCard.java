package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.util.LocalDatePersistenceConverter;
import com.realdolmen.rdtravel.validation.CreditCardNumber;
import com.realdolmen.rdtravel.validation.Future;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
@Entity
public class CreditCard extends PaymentMethod {
    @Convert(converter = LocalDatePersistenceConverter.class)
    @NotNull
    @Column(nullable = false)
    @Future
    private LocalDate expirationDate;
    @NotNull
    @Column(nullable = false)
    @CreditCardNumber
    private Long number;

    public CreditCard(LocalDate expirationDate, Long number) {
        this.expirationDate = expirationDate;
        this.number = number;
    }

    public CreditCard() {
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
