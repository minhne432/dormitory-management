//filepath = src/main/java/com/example/demo/repository/BillRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    List<Bill> findByStudentStudentIdAndIssueDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);
    List<Bill> findByStudentStudentId(Long studentId);
}
