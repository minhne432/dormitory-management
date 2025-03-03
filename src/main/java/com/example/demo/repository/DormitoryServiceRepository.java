package com.example.demo.repository;

import com.example.demo.entity.DormitoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface DormitoryServiceRepository extends JpaRepository<DormitoryService, Long> {
    boolean existsByServiceName(String serviceName);
    List<DormitoryService> findByVisibleForStudentTrue();

    DormitoryService findByServiceName(String serviceName);
}
