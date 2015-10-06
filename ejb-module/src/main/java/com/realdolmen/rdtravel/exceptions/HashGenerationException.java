package com.realdolmen.rdtravel.exceptions;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class HashGenerationException extends RuntimeException {
    public HashGenerationException(String message){
        super(message);
    }

    public HashGenerationException(){
        super("Could not generate a hash from the given String.");
    }
}
