package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomAssignment;
import com.example.demo.repository.RoomAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentRoomService {

    private final RoomAssignmentRepository assignmentRepo;
    private final StudentService studentService;   // đã có trong dự án

    /** Thông tin phòng hiện tại của sinh viên đang đăng nhập */
    public Optional<Room> getCurrentRoomForLoggedStudent() {
        Long studentId = studentService.getCurrentStudentId();
        RoomAssignment ra = assignmentRepo
                .findByStudentStudentIdAndEndDateIsNull(studentId);
        return Optional.ofNullable(ra).map(RoomAssignment::getRoom);
    }
}
