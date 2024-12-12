package com.example.exception.model;


import com.example.exception.config.ErrorDetailsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@JsonSerialize(using = ErrorDetailsSerializer.class)
public class ErrorDetails {
    private ErrorCode errorCode;
    private String errorMessage;
}
