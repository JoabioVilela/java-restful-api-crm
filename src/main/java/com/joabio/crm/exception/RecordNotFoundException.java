package com.joabio.crm.exception;

public class RecordNotFoundException extends RuntimeException {
    
    public RecordNotFoundException( Long id) {
        super("Could not find record " + id);
    }
}