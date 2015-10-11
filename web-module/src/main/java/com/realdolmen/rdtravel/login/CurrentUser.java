package com.realdolmen.rdtravel.login;

import com.realdolmen.rdtravel.domain.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by DWSAX40 on 11/10/2015.
 */
@Named
@RequestScoped
public class CurrentUser {
    @Inject private User user;

    public User getUser() {
        return user;
    }
}
