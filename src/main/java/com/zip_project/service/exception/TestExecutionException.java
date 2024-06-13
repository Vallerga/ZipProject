package com.zip_project.service.exception;

import org.springframework.http.HttpStatus;

public class TestExecutionException extends CustomException {
    private static final long serialVersionUID = 6L;

    public TestExecutionException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
