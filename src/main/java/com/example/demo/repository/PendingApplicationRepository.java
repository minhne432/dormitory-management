// src/main/java/com/example/demo/repository/PendingApplicationRepository.java
package com.example.demo.repository;

import com.example.demo.entity.PendingApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PendingApplicationRepository extends JpaRepository<PendingApplication, Long>, JpaSpecificationExecutor<PendingApplication> {
    // Không cần thêm phương thức nào nếu sử dụng Specifications
}
