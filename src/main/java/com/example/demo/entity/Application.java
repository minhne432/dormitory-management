package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Manager approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    // Thay thế trường dormitoryArea bằng liên kết đến Dormitory qua khóa ngoại dorm_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dorm_id", nullable = true)
    private Dormitory dormitory;

    @Column(name = "note", length = 255)
    private String note;

    public enum ApplicationStatus {
        approved,
        pending,
        rejected,
        completed
    }
}
