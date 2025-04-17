package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('completed','pending') DEFAULT 'pending'")
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    public enum PaymentStatus {
        completed, pending
    }
    @Column(name = "vnp_txn_ref", length = 50)
    private String vnpTxnRef;
}
