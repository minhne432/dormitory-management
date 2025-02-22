package com.example.demo.repository;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, RoomAssignmentId> {
    // Hoặc dùng @IdClass/ @EmbeddedId, tuỳ bạn
    // Lấy phân phòng hiện tại của sinh viên (chưa có ngày kết thúc)
    RoomAssignment findByStudentStudentIdAndEndDateIsNull(Long studentId);
    List<RoomAssignment> findAllByEndDateIsNull();

    List<RoomAssignment> findByRoomRoomIdAndEndDateIsNull(Long roomId);

    // Lấy danh sách sinh viên theo Room
    @Query("SELECT ra.student FROM RoomAssignment ra WHERE ra.room = :room")
    List<Student> findStudentsByRoom(@Param("room") Room room);

}