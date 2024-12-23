package com.example.demo.repository;

import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, RoomAssignmentId> {
    // Hoặc dùng @IdClass/ @EmbeddedId, tuỳ bạn
}