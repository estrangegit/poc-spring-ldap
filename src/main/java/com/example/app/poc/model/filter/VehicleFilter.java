package com.example.app.poc.model.filter;

import com.example.app.poc.validation.BrandConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleFilter {
    private String licencePlate;
    @BrandConstraint
    private String brand;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")   
    private LocalDateTime minIssuanceDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime maxIssuanceDate;
    @Pattern(regexp = "licencePlate|brand|issuanceDate", message = "possible sort keys are licencePlate, brand or issuanceDate")
    private String sortKey;
    @Pattern(regexp = "asc|desc", message = "sortDirections available values are 'asc' and 'desc'")
    private String sortDirection;
    @NotNull(message = "page number must not be null")
    @Min(value = 0, message = "page number must be at least 0")
    private Integer page;
    @NotNull(message = "page size must not be null")
    @Min(value = 0, message = "page size must be at least 0")
    @Max(value = 50, message = "page size must be at most 50")
    private Integer size;
}
