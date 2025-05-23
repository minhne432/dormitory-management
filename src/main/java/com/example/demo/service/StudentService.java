package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomAssignment;
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

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Long getCurrentRoomId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        RoomAssignment currentAssignment = roomAssignmentRepository.findByStudentStudentIdAndEndDateIsNull(student.getStudentId());

        if (currentAssignment != null) {
            return currentAssignment.getRoom().getRoomId();
        }

        return null; // Sinh viên chưa được phân phòng
    }

    /**
     * Lấy email của sinh viên hiện tại đang đăng nhập.
     *
     * @return Email của sinh viên.
     * @throws IllegalStateException nếu không tìm thấy sinh viên.
     */
    public String getCurrentStudentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("User is not authenticated");
        }

        String username = auth.getName();
        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        return student.getEmail();
    }

   //get email by studentId
    public String getEmailBy(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        return student.getEmail();
    }

    //getcurrentstudent
    public Student getCurrentStudent() {
        Long studentId = getCurrentStudentId();
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));
    }

    public RoomAssignment getCurrentRoomAssignment() {
        Long studentId = getCurrentStudentId();
        return roomAssignmentRepository.findByStudentStudentIdAndEndDateIsNull(studentId);
    }




}
