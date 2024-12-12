package com.example.app.poc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_poc_brd_brand", schema = "poc")
@Getter
@Setter
public class Brand {
    @Id
    @Column(name = "brd_code", nullable = false)
    private String code ;

    @Column(name = "brd_name", nullable = false)
    private String name;
}
