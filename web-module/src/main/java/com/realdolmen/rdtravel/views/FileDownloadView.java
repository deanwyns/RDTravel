package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.controllers.TripController;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;

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
    public void init() {
        tripController.exportTripsToFile();

        InputStream stream = this.getClass().getResourceAsStream("/realdolmen/css/main.css");
        file = new DefaultStreamedContent(stream, "text/css", "main.css");
    }

    public StreamedContent getFile() {
        return file;
    }
}
