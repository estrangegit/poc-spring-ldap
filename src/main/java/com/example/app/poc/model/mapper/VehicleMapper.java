package com.example.app.poc.model.mapper;

import com.example.app.poc.model.Vehicle;
import com.example.app.poc.model.dto.VehicleDto;
import com.example.app.poc.service.IDateUtilitarianService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class VehicleMapper {
    
    @Autowired
    private IDateUtilitarianService dateUtilitarianService;

    @Mapping(source = "brand.name", target="brand")
    @Mapping(source = "issuanceDate", target = "issuanceDate", qualifiedByName = "formatStandard")
    public abstract VehicleDto vehicleToVehicleDto(Vehicle vehicle);

    public abstract List<VehicleDto> vehiclesToVehicleDtos(List<Vehicle> vehicles);

    @Named("formatStandard")
    public String formatStandard(LocalDateTime issuancedate) {
        return this.dateUtilitarianService.formatStandard(issuancedate);
    }
}
