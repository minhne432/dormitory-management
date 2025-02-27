package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "STUDENT_SERVICE_REGISTRATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class StudentServiceRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DormitoryService dormitoryService;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('pending', 'approved', 'rejected') DEFAULT 'pending'")
    private RegistrationStatus status;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Manager approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    // Ghi nhận số lượng sử dụng dịch vụ thực tế
    @Column(name = "actual_quantity")
    private Integer actualQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoiced", columnDefinition = "ENUM('NO','YES') NOT NULL")
    private StudentServiceRegistration.InvoicedStatus invoiced;


    public enum RegistrationStatus {
        pending, approved, rejected
    }

    public enum InvoicedStatus {
        NO, YES
    }

}
