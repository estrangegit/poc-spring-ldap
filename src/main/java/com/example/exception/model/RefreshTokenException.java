package com.example.exception.model;

import org.springframework.http.HttpStatus;

public class RefreshTokenException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.FORBIDDEN;
    public RefreshTokenException(String message) {
        super(message);
    }
    public RefreshTokenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
