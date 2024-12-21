package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SERVICES")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name", length = 100)
    private String serviceName;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "description", length = 255)
    private String description;

    // Constructors, getters, setters...
}
