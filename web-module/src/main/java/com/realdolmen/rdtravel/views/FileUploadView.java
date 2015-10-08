package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.controllers.TripController;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class FileUploadView implements Serializable{

    @Inject
    private TripController tripController;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if(file != null) {
            tripController.parseAndPersistTrip(file.getContents());
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Failed upload","File could not be uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}