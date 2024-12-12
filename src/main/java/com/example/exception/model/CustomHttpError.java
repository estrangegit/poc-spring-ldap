package com.example.exception.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomHttpError {
    private Date timestamp;
    private int status;
    private String error;
    private String path;
    private ErrorDetails customError;
}
