package com.zip_project.service.exception;

import org.springframework.http.HttpStatus;

public class DataParsingException extends CustomException {
    private static final long serialVersionUID = 4L;

    public DataParsingException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}