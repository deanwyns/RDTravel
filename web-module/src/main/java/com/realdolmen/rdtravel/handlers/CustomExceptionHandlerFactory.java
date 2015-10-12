package com.realdolmen.rdtravel.handlers;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Created by JSTAX29 on 12/10/2015.
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
    private ExceptionHandlerFactory parent;

    // this injection handles jsf
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(parent.getExceptionHandler());
    }

}