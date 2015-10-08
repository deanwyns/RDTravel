package com.realdolmen.rdtravel.principals;

import com.realdolmen.rdtravel.domain.Customer;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public class CustomerPrincipal extends UserPrincipal {
    public CustomerPrincipal(String name, Customer user) {
        super(name, user);
    }

    @Override
    public String getRoleName() {
        return "Customer";
    }
}
