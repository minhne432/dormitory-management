// src/main/java/com/example/demo/entity/PendingApplication.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "pending_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PendingApplication {

    @Id
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "dormitory_area")
    private String dormitoryArea;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "department")
    private String department;

    @Column(name = "phone_number")
    private String phoneNumber;



    // Getters và Setters
    // ...
}
