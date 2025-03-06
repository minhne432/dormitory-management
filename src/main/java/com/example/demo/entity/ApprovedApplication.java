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


    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "dormitory_area")
    private String dormitoryArea;

}