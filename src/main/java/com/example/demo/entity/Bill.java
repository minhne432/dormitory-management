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

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    @ToString.Exclude
    private Student student;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BillItem> billItems = new ArrayList<>();

    // Thêm trường phân loại hóa đơn: ENUM('phòng','điện-nước','dịch-vụ')
    @Convert(converter = BillTypeConverter.class)
    @Column(name = "bill_type", columnDefinition = "ENUM('phòng','điện-nước','dịch-vụ')")
    private BillType billType;

    public enum BillStatus {
        overdue, paid, unpaid
    }

    public enum BillType {
        PHONG, DIEN_NUOC, DICH_VU
    }

    // Tính tổng tiền hóa đơn dựa trên các BillItem (tuỳ logic)
    public void calculateTotalAmount() {
        double sum = 0.0;
        for (BillItem item : billItems) {
            sum += item.getAmount();
        }
        this.totalAmount = sum;
    }
}

