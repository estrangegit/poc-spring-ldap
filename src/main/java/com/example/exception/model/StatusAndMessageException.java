package com.example.exception.model;

import org.springframework.http.HttpStatus;

public interface StatusAndMessageException {
    HttpStatus getStatus();
    String getMessage();
}
