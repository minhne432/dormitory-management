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
    @JoinColumn(name = "bill_id", nullable = false)
    @ToString.Exclude
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DormitoryService service;

    @Column(name = "amount")
    private Double amount;

    // Nếu bạn muốn ghi nhận "quantity" và "unitPrice"
    // @Column(name = "quantity", nullable = false)
    // private Integer quantity;
    //
    // @Column(name = "unit_price", nullable = false)
    // private Double unitPrice;
}
