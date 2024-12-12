package com.example.exception.model;

import org.springframework.http.HttpStatus;

public class SignatureAuthorizationException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.FORBIDDEN;
    public SignatureAuthorizationException(String message) {
        super(message);
    }
    public SignatureAuthorizationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
