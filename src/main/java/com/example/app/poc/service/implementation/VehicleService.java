package com.example.app.poc.service.implementation;

import com.example.app.poc.model.Vehicle;
import com.example.app.poc.model.dto.PageDto;
import com.example.app.poc.model.dto.VehicleDto;
import com.example.app.poc.model.filter.VehicleFilter;
import com.example.app.poc.model.mapper.VehicleMapper;
import com.example.app.poc.model.specification.VehicleSpecification;
import com.example.app.poc.repository.VehicleRepository;
import com.example.app.poc.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {
    
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    
    public PageDto<VehicleDto> searchVehicle(VehicleFilter vehicleFilter) {
        Specification<Vehicle> vehicleSpecification = new VehicleSpecification(vehicleFilter);
        Pageable pageable = PageRequest.of(vehicleFilter.getPage(), vehicleFilter.getSize());
        Page<Vehicle> vehiclePage =  this.vehicleRepository.findAll(vehicleSpecification, pageable);
        PageDto<VehicleDto> pageDto = new PageDto<>();
        pageDto.setPageNumber(vehiclePage.getNumber());
        pageDto.setPageSize(vehiclePage.getSize());
        pageDto.setTotalPages(vehiclePage.getTotalPages());
        pageDto.setTotalNbElements(vehiclePage.getTotalElements());
        List<VehicleDto> vehicleDtos = this.vehicleMapper.vehiclesToVehicleDtos(vehiclePage.getContent());
        pageDto.setContent(vehicleDtos);
        return pageDto;
    }
}
