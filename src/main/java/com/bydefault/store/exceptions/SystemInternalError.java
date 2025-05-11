package com.bydefault.store.exceptions;

public class SystemInternalError extends RuntimeException {
    private String message;
    public SystemInternalError(String message) {
        super(message);
    }
}
