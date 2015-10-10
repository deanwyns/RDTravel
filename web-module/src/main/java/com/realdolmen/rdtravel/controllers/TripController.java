package com.realdolmen.rdtravel.controllers;


import com.realdolmen.rdtravel.exceptions.FlightNotFoundException;
import com.realdolmen.rdtravel.exceptions.FlightOutsideTripDateException;
import com.realdolmen.rdtravel.services.ImportExportTripService;
import org.jdom2.JDOMException;

import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Named
@RequestScoped
public class TripController {

    @Inject
    private ImportExportTripService importExportTripService;


    /**
     * Delegates the functionality of parsing and persisting an XML file of trips to the importExportTripService.
     * @param contents the contents of the xml file
     */
    public void parseAndPersistTrip(byte[] contents) {
        try {
            //Let the service import the trips.
            importExportTripService.parseAndPersistTrip(contents);

            //Let the user know creating/updating was successful
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload successful", "File was uploaded and trips have been created and/or updated.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (JAXBException | UnsupportedEncodingException | NumberFormatException | ClassCastException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parse failed", "File could not be parsed as a trip.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (PersistenceException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", "The trip could not be saved to the database.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (EJBTransactionRolledbackException e) {
            if(e.getCause() instanceof ConstraintViolationException){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", "Every trip should have a flight.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }else{
                throw e;
            }
        }catch (FlightNotFoundException | FlightOutsideTripDateException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persist failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException | JDOMException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delegates the fucntionality of exporting trips to an xml file to the importExportTripService.
     */
    public File exportTripsToFile(){
        try {
            return importExportTripService.exportTripsToXmlFile();
        } catch (JAXBException | URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
