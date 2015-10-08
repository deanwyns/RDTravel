package com.realdolmen.rdtravel.domain;

import javax.persistence.*;

/**
 * Created by JSTAX29 on 2/10/2015.
 * The method of a payment.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
}
