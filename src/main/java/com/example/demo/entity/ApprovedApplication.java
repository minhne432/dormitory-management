// src/main/java/com/example/demo/entity/ApprovedApplication.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "approved_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ApprovedApplication {

    @Id
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "department")
    private String department;

    @Column(name = "student_class")
    private String studentClass;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    /**
     * Nếu muốn map enum status, ta dùng lại enum của Application,
     * hoặc tạo enum riêng có giá trị y hệt (approved, pending, rejected, completed).
     * Ở đây ví dụ dùng chung enum của Application.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Application.ApplicationStatus status;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "dormitory_area")
    private String dormitoryArea;

    @Column(name = "note")
    private String note;
}