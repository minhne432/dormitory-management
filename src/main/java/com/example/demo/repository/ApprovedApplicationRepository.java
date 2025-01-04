package com.example.demo.repository;

import com.example.demo.entity.ApprovedApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApprovedApplicationRepository
        extends JpaRepository<ApprovedApplication, Long>, JpaSpecificationExecutor<ApprovedApplication> {
    // JpaSpecificationExecutor cho phép sử dụng Specification để lọc dữ liệu linh hoạt
}
