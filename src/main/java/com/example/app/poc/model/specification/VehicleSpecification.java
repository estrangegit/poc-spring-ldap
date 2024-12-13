package com.example.app.poc.model.specification;

import com.example.app.poc.model.Brand;
import com.example.app.poc.model.Vehicle;
import com.example.app.poc.model.enumeration.ESortDirection;
import com.example.app.poc.model.filter.VehicleFilter;
import com.google.common.collect.Lists;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class VehicleSpecification implements Specification<Vehicle> {
    
    private final VehicleFilter vehicleFilter;
    
    public VehicleSpecification(VehicleFilter vehicleFilter) {
        super();
        this.vehicleFilter = vehicleFilter;
    }

    private static final String LICENCE_PLATE = "licencePlate";
    private static final String BRAND = "brand";
    private static final String CODE = "code";
    private static final String ISSANCE_DATE = "issuanceDate";
    
    @Override
    public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        
        List<Predicate> predicates = Lists.newArrayList();

        if(vehicleFilter.getLicencePlate() != null  && !vehicleFilter.getLicencePlate().trim().isEmpty()) {
            Predicate predicate = criteriaBuilder.like(root.get(LICENCE_PLATE), "%" + vehicleFilter.getLicencePlate() + "%");
            predicates.add(predicate);
        }
        
        if(vehicleFilter.getBrand() != null) {
            Join<Vehicle, Brand> childJoin = root.join(BRAND);
            Predicate predicate = criteriaBuilder.equal(childJoin.get(CODE), vehicleFilter.getBrand());
            predicates.add(predicate);
        }
        
        if(vehicleFilter.getMinIssuanceDate() != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(ISSANCE_DATE), vehicleFilter.getMinIssuanceDate());
            predicates.add(predicate);
        }

        if(vehicleFilter.getMaxIssuanceDate() != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get(ISSANCE_DATE), vehicleFilter.getMaxIssuanceDate());
            predicates.add(predicate);
        }
        
        if(Lists.newArrayList(LICENCE_PLATE, BRAND, ISSANCE_DATE).contains(vehicleFilter.getSortKey())) {
            if(ESortDirection.ASC.getValue().equals(vehicleFilter.getSortDirection())){
                query.orderBy(criteriaBuilder.asc(root.get(vehicleFilter.getSortKey())));    
            } else if(ESortDirection.DESC.getValue().equals(vehicleFilter.getSortDirection())) {
                query.orderBy(criteriaBuilder.desc(root.get(vehicleFilter.getSortKey())));
            }
        }
        
        Predicate finalPredicate = null;
        for(Predicate predicate: predicates) {
            if(finalPredicate == null) {
                finalPredicate = predicate;
            } else {
                finalPredicate = criteriaBuilder.and(finalPredicate, predicate);                
            }
        }

        return finalPredicate;
    }
}
