package com.example.demo.repository;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Room;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByVnpTxnRef(String vnpTxnRef);

    List<Payment> findByBill_Student(Student student);

    @Query("SELECT p FROM Payment p WHERE p.bill.student = :student OR p.bill.room = :room")
    List<Payment> findByStudentOrRoom(@Param("student") Student student, @Param("room") Room room);


}
