package com.zip_project.service.exception;

import org.springframework.http.HttpStatus;

public class SchemaValidationException extends CustomException {
    private static final long serialVersionUID = 3L;

    public SchemaValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}