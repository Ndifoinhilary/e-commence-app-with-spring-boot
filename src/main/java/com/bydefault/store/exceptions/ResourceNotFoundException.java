package com.bydefault.store.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String message;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
