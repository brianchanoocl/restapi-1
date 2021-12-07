package com.afs.restapi.exception;

public class NoCompanyFoundException extends RuntimeException {
    public NoCompanyFoundException() {
        super("No Company Found");
    }
}
