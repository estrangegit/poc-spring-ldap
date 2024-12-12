package com.example.app.poc.controller;

import com.example.app.poc.model.dto.PageDto;
import com.example.app.poc.model.dto.VehicleDto;
import com.example.app.poc.model.filter.VehicleFilter;
import com.example.app.poc.service.IVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role1")
@RequiredArgsConstructor
public class VehicleController {
    private final IVehicleService vehicleService;

    @PostMapping("/vehicles")
    public ResponseEntity<PageDto<VehicleDto>> searchVehicles(@RequestBody @Valid VehicleFilter vehicleFilter) {
        PageDto<VehicleDto> vehicles = this.vehicleService.searchVehicle(vehicleFilter);
        return ResponseEntity.status(200).body(vehicles);
    }
}
