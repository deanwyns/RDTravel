package com.realdolmen.rdtravel.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.security.Principal;

/**
 * Created by JSTAX29 on 2/10/2015.
 * An administrator of seperate airline companies
 */
@Entity
public class PartnerAdmin extends Administrator {
    @ManyToOne
    private Partner partner;

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
