package com.example.demo.service.impl;

import com.example.demo.entity.StudentServiceRequest;
import com.example.demo.repository.StudentServiceRequestRepository;
import com.example.demo.service.StudentServiceRequestService;
import com.example.demo.specifications.StudentServiceRequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudentServiceRequestServiceImpl implements StudentServiceRequestService {

    private final StudentServiceRequestRepository repository;

    @Autowired
    public StudentServiceRequestServiceImpl(StudentServiceRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<StudentServiceRequest> getStudentServiceRequests(Long studentId, LocalDate from, LocalDate to, String serviceName, Pageable pageable) {
        Specification<StudentServiceRequest> spec = Specification.where(StudentServiceRequestSpecification.hasStudentId(studentId))
                .and(StudentServiceRequestSpecification.hasServiceStartDateBetween(from, to))
                .and(StudentServiceRequestSpecification.hasServiceName(serviceName));
        return repository.findAll(spec, pageable);
    }
}
