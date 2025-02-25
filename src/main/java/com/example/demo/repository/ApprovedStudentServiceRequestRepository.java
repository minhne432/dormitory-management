package com.example.demo.repository;

import com.example.demo.entity.ApprovedStudentServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApprovedStudentServiceRequestRepository
        extends JpaRepository<ApprovedStudentServiceRequest, Long>, JpaSpecificationExecutor<ApprovedStudentServiceRequest> {
}
