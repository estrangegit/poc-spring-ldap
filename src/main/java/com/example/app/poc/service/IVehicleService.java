package com.example.app.poc.service;

import com.example.app.poc.model.dto.PageDto;
import com.example.app.poc.model.dto.VehicleDto;
import com.example.app.poc.model.filter.VehicleFilter;

public interface IVehicleService {
    PageDto<VehicleDto> searchVehicle(VehicleFilter vehicleFilter);
}
