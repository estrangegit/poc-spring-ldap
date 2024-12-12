package com.example.app.poc.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PageDto<T> {
    int totalPages;
    long totalNbElements;
    int pageSize;
    int pageNumber;
    List<T> content;
}
