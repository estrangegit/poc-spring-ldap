package com.example.app.poc.controller;

import com.example.app.poc.model.dto.BrandDto;
import com.example.app.poc.service.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role1")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandService brandService;

    @GetMapping("/brands")
    public ResponseEntity<List<BrandDto>> getBrands() {
        List<BrandDto> brands = this.brandService.getBrands();
        return ResponseEntity.status(200).body(brands);
    }
}
