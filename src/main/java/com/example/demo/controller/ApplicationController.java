package com.example.demo.controller;

import com.example.demo.dto.DormApplicationForm;
import com.example.demo.entity.Application;
import com.example.demo.entity.Application.ApplicationStatus;
import com.example.demo.entity.Student;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ApplicationController {

    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationController(StudentRepository studentRepository,
                                 ApplicationRepository applicationRepository) {
        this.studentRepository = studentRepository;
        this.applicationRepository = applicationRepository;
    }

    // Giả sử đường dẫn GET: /register-dormitory
    @GetMapping("/register-dormitory")
    public String showDormForm(Model model) {
        // Giả định: ta lấy studentId từ session hoặc authentication (vd: user đang login)
        Long currentStudentId = getCurrentStudentId(); // Tuỳ vào logic của bạn

        Optional<Student> optionalStudent = studentRepository.findById(currentStudentId);
        if (optionalStudent.isEmpty()) {
            // Chưa có student => redirect /error
            return "redirect:/error";
        }

        Student student = optionalStudent.get();
        DormApplicationForm form = new DormApplicationForm();

        // Gắn thông tin của Student vào form
        form.setFullName(student.getFullName());
        form.setDepartment(student.getDepartment());
        form.setStudentClass(student.getStudentClass());
        form.setPhoneNumber(student.getPhoneNumber());
        form.setEmail(student.getEmail());
        form.setAddress(student.getAddress());
        // dormitoryArea còn trống hoặc logic khác

        model.addAttribute("student", form);
        return "student/application/registerDormitory"; // Tên file Thymeleaf, vd: registerDormitory.html
    }

    // POST: /register-dormitory
    @PostMapping("/register-dormitory")
    public String processDormRegistration(
            @ModelAttribute("student") DormApplicationForm form,
            Model model
    ) {
        Long currentStudentId = getCurrentStudentId(); // Lấy ID student đang đăng nhập

        // Lấy Student
        Optional<Student> optionalStudent = studentRepository.findById(currentStudentId);
        if (optionalStudent.isEmpty()) {
            model.addAttribute("error", "Student not found!");
            return "registerDormitory";
        }
        Student student = optionalStudent.get();

        // Cập nhật những thông tin mà sinh viên có thể chỉnh (sđt, email, etc.)
        student.setPhoneNumber(form.getPhoneNumber());
        student.setEmail(form.getEmail());
        // address, fullName, department,... có thể là readonly => không update

        // Lưu Student (nếu có thay đổi)
        studentRepository.save(student);

        // Tạo 1 Application (đơn đăng ký) mới
        Application application = new Application();
        application.setStudent(student);
        application.setSubmissionDate(LocalDate.now());
        application.setStatus(ApplicationStatus.pending);
        // Chưa có approvedBy, approvalDate, note => vì manager chưa duyệt

        // Bạn có thể mở rộng cột “dormitory_area” nếu muốn.
        // Hoặc tạm thời gắn "Khu A/B/C" vào note,
        // hoặc thêm cột dorm_area vào entity Application:
        application.setDormitoryArea(form.getDormitoryArea());
        application.setNote(form.getNote());

        applicationRepository.save(application);

        // Sau khi lưu xong -> redirect sang trang hiển thị kết quả
        return "redirect:/register-dormitory-success";
    }

    // Trang thông báo đăng ký thành công
    @GetMapping("/register-dormitory-success")
    public String dormRegisterSuccess() {
        return "student/application/registerDormitorySuccess"; // 1 trang HTML đơn giản
    }

    // Giả sử bạn có phương thức này để lấy studentId từ session (hoặc SecurityContext)
    private Long getCurrentStudentId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem user đã được xác thực chưa
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("User is not authenticated");
        }

        // Lấy username từ Authentication
        String username = auth.getName();

        // Tìm student theo username
        Optional<Student> optionalStudent = studentRepository.findByUsername(username);
        if (optionalStudent.isEmpty()) {
            throw new IllegalStateException("No student found for the current user");
        }

        // Trả về studentId
        return optionalStudent.get().getStudentId();
    }


}
