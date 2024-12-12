package com.example.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomTestException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public CustomTestException(String message) {
        super(message);
    }
    public CustomTestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
