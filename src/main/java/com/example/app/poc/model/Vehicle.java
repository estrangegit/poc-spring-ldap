package com.example.app.poc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_poc_vhl_vehicle", schema = "poc")
@Getter
@Setter
public class Vehicle {
    @Id
    @SequenceGenerator(name = "vehicle_sequence_generator", sequenceName = "seq_pk_vhl", allocationSize = 1, schema = "poc")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence_generator")
    @Column(name = "vhl_id", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "vhl_licence_plate", nullable = false)
    private String licencePlate;
    
    @ManyToOne
    @JoinColumn(name = "vhl_brd_code", nullable = false)
    private Brand brand;

    @Column(name = "vhl_issuance_date")
    private LocalDateTime issuanceDate;
}
