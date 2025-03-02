package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bill_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_item_id")
    private Long billItemId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DormitoryService service;


    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @ToString.Exclude
    private Bill bill;

    // Thay thế quan hệ cũ (service_id) bằng quan hệ tới StudentServiceRegistration
    @ManyToOne
    @JoinColumn(name = "registration_id", nullable = true)
    private StudentServiceRegistration registration;



    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount")
    private Double amount;
}
