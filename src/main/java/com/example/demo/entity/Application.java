package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "APPLICATIONS")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status; // 'pending','approved','rejected'

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Manager approvedBy;  // có thể null

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "note", length = 255)
    private String note;

    public enum ApplicationStatus {
        pending, approved, rejected
    }

    // Constructors, getters, setters...
}
