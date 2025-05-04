package com.bydefualt.store.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String code;
    private String path;
}
