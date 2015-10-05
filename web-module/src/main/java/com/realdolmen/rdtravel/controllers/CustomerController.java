package com.realdolmen.rdtravel.controllers;

import com.realdolmen.rdtravel.domain.Customer;
import com.realdolmen.rdtravel.persistence.CustomerDAO;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
@Named
@RequestScoped
public class CustomerController {

    @Inject
    private CustomerDAO customerDAO;
    private Customer customer = new Customer();

    public String createCustomer(){
        try{
            customerDAO.create(customer);
        }catch (EJBException ejbe){
            if(ejbe.getCause() instanceof PersistenceException){
                FacesMessage message = new FacesMessage("This email already exists.");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("registerForm:c_email", message);
                return null;
            }
            throw ejbe;
        }

        return "login?faces-redirect=true";
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
