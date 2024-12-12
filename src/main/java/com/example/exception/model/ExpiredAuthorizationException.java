package com.example.exception.model;

import org.springframework.http.HttpStatus;

public class ExpiredAuthorizationException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.FORBIDDEN;
    public ExpiredAuthorizationException(String message) {
        super(message);
    }
    public ExpiredAuthorizationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
