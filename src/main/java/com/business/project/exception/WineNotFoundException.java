package com.business.project.exception;

public class WineNotFoundException extends RuntimeException {

    public WineNotFoundException(String message) {
        super(message);
    }
}
