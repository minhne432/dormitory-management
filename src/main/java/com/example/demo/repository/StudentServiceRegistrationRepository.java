package com.example.demo.repository;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.StudentServiceRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentServiceRegistrationRepository extends JpaRepository<StudentServiceRegistration, Long> {
    @Query("SELECT r FROM StudentServiceRegistration r JOIN FETCH r.student WHERE r.id IN :ids")
    List<StudentServiceRegistration> findAllByIdsWithStudent(@Param("ids") List<Long> ids);
    List<StudentServiceRegistration> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Sử dụng approvalDate thay vì recordDate
    List<StudentServiceRegistration> findByApprovalDateBetween(LocalDate startDate, LocalDate endDate);
}