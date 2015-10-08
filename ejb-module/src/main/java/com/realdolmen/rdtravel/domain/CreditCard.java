package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.util.LocalDatePersistenceConverter;
import org.hibernate.validator.constraints.CreditCardNumber;

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
    private LocalDate expirationDate;
    @NotNull
    @Column(nullable = false)
    @CreditCardNumber
    private String number;

    public CreditCard(LocalDate expirationDate, String number) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
