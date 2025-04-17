package com.example.demo.repository;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <- cần thêm
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomAssignmentRepository extends
        JpaRepository<RoomAssignment, RoomAssignmentId>,
        JpaSpecificationExecutor<RoomAssignment> { // <- THÊM DÒNG NÀY để hỗ trợ Specification

    RoomAssignment findByStudentStudentIdAndEndDateIsNull(Long studentId);

    List<RoomAssignment> findAllByEndDateIsNull();

    List<RoomAssignment> findByRoomRoomIdAndEndDateIsNull(Long roomId);

    @Query("SELECT ra.student FROM RoomAssignment ra WHERE ra.room = :room")
    List<Student> findStudentsByRoom(@Param("room") Room room);

}
