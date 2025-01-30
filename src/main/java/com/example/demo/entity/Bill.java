package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "BILLS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DormitoryService dormitoryService; // Trỏ trực tiếp đến dịch vụ

    @Column(name = "billing_period", length = 10)
    private String billingPeriod; // ví dụ '2024-12'

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BillStatus status; // 'unpaid','paid','overdue'

    public enum BillStatus {
        unpaid, paid, overdue
    }

    // Constructors, getters, setters...
}
