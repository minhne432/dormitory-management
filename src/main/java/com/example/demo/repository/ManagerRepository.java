package com.example.demo.repository;

import com.example.demo.entity.Manager;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    // Tìm Student thông qua username trong User
    @Query("SELECT s FROM Manager s WHERE s.user.username = :username")
    Optional<Manager> findByUsername(String username);
}
