package com.realdolmen.rdtravel.producers;

import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.principals.UserPrincipal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**s
 * Created by DWSAX40 on 8/10/2015.
 */
@RequestScoped
public class UserProducer implements Serializable {
    @Produces
    public User getUser() {
        return ((UserPrincipal)FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()).getUser();
    }
}
