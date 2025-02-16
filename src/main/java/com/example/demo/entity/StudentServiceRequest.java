package com.example.demo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student_service_requests") // Ánh xạ đến view hoặc bảng trong MySQL
public class StudentServiceRequest {

    @Id
    @Column(name = "registration_id", nullable = false)
    private Long registrationId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "student_class", length = 50)
    private String studentClass;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "room_end_date")
    private LocalDate roomEndDate;

    @Column(name = "service_start_date", nullable = false)
    private LocalDate serviceStartDate;

    @Column(name = "service_end_date")
    private LocalDate serviceEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_status", nullable = false)
    private ServiceStatus serviceStatus;

    @Column(name = "service_name", nullable = false)
    private String serviceName;
    public enum ServiceStatus {
        pending, approved, rejected
    }
}
