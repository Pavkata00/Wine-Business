package com.business.project.exception;

public class FactoryNotFoundException extends RuntimeException {

    public FactoryNotFoundException(String message) {
        super(message);
    }
}
