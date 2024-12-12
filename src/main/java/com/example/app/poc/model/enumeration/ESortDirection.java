package com.example.app.poc.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ESortDirection {
    ASC("asc"),
    DESC("desc");

    private String value;
}
