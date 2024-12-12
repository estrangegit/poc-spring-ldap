package com.example.app.poc.validation;

import com.example.app.poc.repository.BrandRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandValidator implements ConstraintValidator<BrandConstraint, String> {
    
    private final BrandRepository brandRepository;
    
    @Override
    public boolean isValid(String brand, ConstraintValidatorContext constraintValidatorContext) {
        return brand == null || this.brandRepository.existsById(brand);
    }
}
