package com.realdolmen.rdtravel.principals;

import com.realdolmen.rdtravel.domain.User;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public class PartnerAdminPrincipal extends UserPrincipal {
    public PartnerAdminPrincipal(String name, User user) {
        super(name, user);
    }

    @Override
    public String getRoleName() {
        return "PartnerAdmin";
    }
}
