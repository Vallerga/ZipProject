package com.zip_project.service.exception;

import org.springframework.http.HttpStatus;

public class DatabaseOperationException extends CustomException {
    private static final long serialVersionUID = 5L;

    public DatabaseOperationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}