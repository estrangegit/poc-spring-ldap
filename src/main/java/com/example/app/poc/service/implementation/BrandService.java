package com.example.app.poc.service.implementation;

import com.example.app.poc.model.Brand;
import com.example.app.poc.model.dto.BrandDto;
import com.example.app.poc.model.mapper.BrandMapper;
import com.example.app.poc.repository.BrandRepository;
import com.example.app.poc.service.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    
    public List<BrandDto> getBrands() {
        List<Brand> brands = this.brandRepository.findAll();
        return this.brandMapper.brandsToBrandDtos(brands);
    }
}
