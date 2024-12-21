package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Tìm Student thông qua username trong User
    @Query("SELECT s FROM Student s WHERE s.user.username = :username")
    Optional<Student> findByUsername(String username);
}
