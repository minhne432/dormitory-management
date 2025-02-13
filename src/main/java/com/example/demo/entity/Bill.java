package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
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

    @Column(name = "billing_period")
    private String billingPeriod; // Ví dụ: "2025-02" hoặc "Feb-2025"

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('overdue','paid','unpaid') DEFAULT 'unpaid'")
    private BillStatus status;

    @Column(name = "total_amount")
    private Double totalAmount;

    // Nếu hoá đơn có gắn với phòng
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Nếu hoá đơn gắn với sinh viên
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Liên kết OneToMany với BillItem
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BillItem> billItems = new ArrayList<>();

    public enum BillStatus {
        overdue, paid, unpaid
    }

    // Tính totalAmount tự động (tuỳ logic)
    public void calculateTotalAmount() {
        double sum = 0.0;
        for (BillItem item : billItems) {
            sum += item.getAmount();
        }
        this.totalAmount = sum;
    }
}
