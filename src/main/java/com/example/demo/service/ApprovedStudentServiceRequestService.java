package com.example.demo.service;

import com.example.demo.dto.ApprovedStudentServiceRequestFilter;
import com.example.demo.entity.ApprovedStudentServiceRequest;
import com.example.demo.repository.ApprovedStudentServiceRequestRepository;
import com.example.demo.specifications.ApprovedStudentServiceRequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApprovedStudentServiceRequestService {

    @Autowired
    private ApprovedStudentServiceRequestRepository repository;

    public Page<ApprovedStudentServiceRequest> getApprovedRequests(ApprovedStudentServiceRequestFilter filter, Pageable pageable) {
        return repository.findAll(ApprovedStudentServiceRequestSpecification.filter(filter), pageable);
    }
}
