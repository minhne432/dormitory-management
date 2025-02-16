package com.example.demo.repository;

import com.example.demo.entity.StudentServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentServiceRequestRepository extends JpaRepository<StudentServiceRequest, Long>, JpaSpecificationExecutor<StudentServiceRequest> {
}
