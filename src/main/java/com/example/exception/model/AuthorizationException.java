package com.example.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.FORBIDDEN;
    public AuthorizationException(String message) {
        super(message);
    }
    public AuthorizationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
