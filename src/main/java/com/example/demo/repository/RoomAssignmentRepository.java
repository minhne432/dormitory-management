package com.example.demo.repository;

import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, RoomAssignmentId> {
    // Hoặc dùng @IdClass/ @EmbeddedId, tuỳ bạn
    // Lấy phân phòng hiện tại của sinh viên (chưa có ngày kết thúc)
    RoomAssignment findByStudentStudentIdAndEndDateIsNull(Long studentId);
    List<RoomAssignment> findAllByEndDateIsNull();
}