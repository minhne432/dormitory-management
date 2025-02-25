package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ApprovedStudentServiceRequestFilter {
    private Long registrationId;
    private Long studentId;
    private String phoneNumber;
    private Long roomId;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String serviceName;
}
