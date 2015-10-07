package com.realdolmen.rdtravel.controllers;


import com.realdolmen.rdtravel.exceptions.TripNotFoundException;
import com.realdolmen.rdtravel.persistence.FlightDAO;
import com.realdolmen.rdtravel.persistence.TripDAO;
import com.realdolmen.rdtravel.services.ImportTripService;
import org.jdom2.JDOMException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Named
@RequestScoped
public class TripController {

    @Inject
    private ImportTripService importTripService;


    public void parseAndPersistTrip(byte[] contents) {
        try {
            //Let the service import the trips.
            importTripService.parseAndPersistTrip(contents);

            //Let the user know creating/updating was successful
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload successful", "File was uploaded and trips have been created and/or updated.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (JAXBException | UnsupportedEncodingException | NumberFormatException | ClassCastException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parse failed", "File could not be parsed as a trip.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (PersistenceException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", "The trip could not be saved to the database.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (TripNotFoundException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException | JDOMException | XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
