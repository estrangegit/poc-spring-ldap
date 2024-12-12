package com.example.app.poc.model.mapper;

import com.example.app.poc.model.Brand;
import com.example.app.poc.model.dto.BrandDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BrandMapper {
    public abstract BrandDto brandToBrandDto(Brand brand);
    public abstract List<BrandDto> brandsToBrandDtos(List<Brand> brands);
}
