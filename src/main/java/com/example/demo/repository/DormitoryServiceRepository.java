package com.example.demo.repository;

import com.example.demo.entity.DormitoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryServiceRepository extends JpaRepository<DormitoryService, Long> {
    boolean existsByServiceName(String serviceName);
}
