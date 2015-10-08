package com.realdolmen.rdtravel.principals;

import com.realdolmen.rdtravel.domain.Administrator;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public class RDTravelAdminPrincipal extends UserPrincipal {
    public RDTravelAdminPrincipal(String name, Administrator user) {
        super(name, user);
    }

    @Override
    public String getRoleName() {
        return "RDTravelAdmin";
    }
}
