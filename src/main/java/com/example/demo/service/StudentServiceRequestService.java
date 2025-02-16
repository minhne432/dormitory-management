package com.example.demo.service;

import com.example.demo.entity.StudentServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StudentServiceRequestService {
    Page<StudentServiceRequest> getStudentServiceRequests(Long studentId, LocalDate from, LocalDate to, String serviceName, Pageable pageable);
}
