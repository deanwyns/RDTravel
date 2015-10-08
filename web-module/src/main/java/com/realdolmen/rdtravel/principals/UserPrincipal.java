package com.realdolmen.rdtravel.principals;

import com.realdolmen.rdtravel.domain.User;
import org.jboss.security.SimplePrincipal;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public abstract class UserPrincipal extends SimplePrincipal {
    private User user;

    public UserPrincipal(String name, User user) {
        super(name);

        this.user = user;
    }

    public abstract String getRoleName();

    public User getUser() {
        return user;
    }
}
