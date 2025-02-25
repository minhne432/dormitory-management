package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Entity
@Table(name = "approved_student_service_requests")
@Getter
@Setter
@Immutable
@NoArgsConstructor
public class ApprovedStudentServiceRequest {

    @Id  // Chỉ định một trường duy nhất làm khóa chính (có thể cần điều chỉnh nếu view không có trường ID rõ ràng)
    private Long registrationId;

    private Long studentId;
    private String fullName;
    private String email;
    private String gender;
    private String phoneNumber;
    private String studentClass;
    private String department;
    private LocalDate dateOfBirth;
    private String address;
    private Long roomId;
    private LocalDate assignedDate;
    private LocalDate roomEndDate;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String serviceStatus;
    private String serviceName;
}
