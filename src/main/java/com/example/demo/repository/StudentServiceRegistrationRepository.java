package com.example.demo.repository;

import com.example.demo.entity.StudentServiceRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentServiceRegistrationRepository extends JpaRepository<StudentServiceRegistration, Long> {
    @Query("SELECT r FROM StudentServiceRegistration r JOIN FETCH r.student WHERE r.id IN :ids")
    List<StudentServiceRegistration> findAllByIdsWithStudent(@Param("ids") List<Long> ids);

}