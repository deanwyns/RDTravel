package com.realdolmen.rdtravel.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
@Entity
public class Endorsement extends PaymentMethod {
    //Validation of this number is out of the scope of the assignment
    @NotNull
    @Size(min = 1, max = 10)
    private String accountNumber;

    public Endorsement(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Endorsement() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
