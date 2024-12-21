package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BILL_DETAILS")
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private Long billDetailId;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "consumption")
    private Double consumption;

    @Column(name = "amount")
    private Double amount;

    // Constructors, getters, setters...
}
