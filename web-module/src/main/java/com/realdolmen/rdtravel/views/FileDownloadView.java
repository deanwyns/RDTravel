package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.controllers.TripController;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 10/10/2015.
 */
@Named
@RequestScoped
public class FileDownloadView {
    @Inject
    private TripController tripController;

    private StreamedContent file;

    @PostConstruct
    public void fileDownloadViewConstruct() {
        try {
            File xmlfile = tripController.exportTripsToFile();
            file = new DefaultStreamedContent(new FileInputStream(xmlfile));
            this.file = new DefaultStreamedContent(new FileInputStream(xmlfile), "text/xml", "trips" + LocalDate.now() + ".xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getFile() {
        return file;
    }
}
