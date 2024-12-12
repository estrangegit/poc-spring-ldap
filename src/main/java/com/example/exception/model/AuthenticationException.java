package com.example.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends RuntimeException implements StatusAndMessageException {
    private HttpStatus status = HttpStatus.UNAUTHORIZED;
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
