package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.entity.Student;
import com.example.demo.repository.RoomAssignmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    private final RoomAssignmentRepository roomAssignmentRepository;
    public StudentService(StudentRepository studentRepository, RoomAssignmentRepository roomAssignmentRepository) {
        this.studentRepository = studentRepository;
        this.roomAssignmentRepository = roomAssignmentRepository;
    }

    public Long getCurrentStudentId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("User is not authenticated");
        }
        String username = auth.getName();
        Optional<Student> optionalStudent = studentRepository.findByUsername(username);
        if (optionalStudent.isEmpty()) {
            throw new IllegalStateException("No student found for the current user");
        }
        return optionalStudent.get().getStudentId();
    }

    /**
     * Lấy thông tin sinh viên dựa trên studentId.
     *
     * @param studentId ID của sinh viên cần tìm.
     * @return Đối tượng Student nếu tìm thấy.
     * @throws IllegalStateException Nếu không tìm thấy sinh viên.
     */
    public Student getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new IllegalStateException("No student found with ID: " + studentId);
        }
        return optionalStudent.get();
    }

    /**
     * Lấy danh sách sinh viên trong một phòng cụ thể
     * @param room đối tượng Room cần tìm danh sách sinh viên
     * @return danh sách sinh viên trong phòng đó
     */
    public List<Student> getStudentsByRoom(Room room) {
        return roomAssignmentRepository.findStudentsByRoom(room);
    }
}
