package com.bydefault.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobeExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, WebRequest request) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        errorResponse.setPath(request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotMatchException(PasswordNotMatchException e, WebRequest request) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        errorResponse.setPath(request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
