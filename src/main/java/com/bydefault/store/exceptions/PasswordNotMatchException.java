package com.bydefault.store.exceptions;

public class PasswordNotMatchException extends RuntimeException {
    private String message;
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
