package com.example.demo.controller;

import com.example.demo.dto.RegistrationFilterDTO;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.service.StudentService;
import com.example.demo.service.StudentServiceRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/student-service-registrations")
public class StudentServiceRegistrationController {

    private final StudentServiceRegistrationService registrationService;

    private final StudentService studentService;
    @Autowired
    public StudentServiceRegistrationController(StudentServiceRegistrationService registrationService, StudentService studentService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
    }

    @PostMapping("/{registrationId}/approve")
    public String approveRegistration(@PathVariable Long registrationId) {
        registrationService.updateRegistrationStatus(registrationId, StudentServiceRegistration.RegistrationStatus.approved);
        // Sau khi cập nhật, chuyển hướng về trang danh sách yêu cầu
        return "redirect:/student-service-requests";
    }

    @PostMapping("/{registrationId}/reject")
    public String rejectRegistration(@PathVariable Long registrationId) {
        registrationService.updateRegistrationStatus(registrationId, StudentServiceRegistration.RegistrationStatus.rejected);
        return "redirect:/student-service-requests";
    }

//     Hiển thị trang liệt kê đăng ký sử dụng dịch vụ của sinh viên với chức năng lọc
    @GetMapping("/list")
    public String listRegistrations(@ModelAttribute("filter") RegistrationFilterDTO filter, Model model, Principal principal) {
        // Lấy username từ Principal (giả sử username chính là studentCode)
        Long studentId = studentService.getCurrentStudentId();
        // Lấy danh sách đăng ký theo studentId và các tiêu chí lọc từ DTO
        List<StudentServiceRegistration> registrations = registrationService.getRegistrationsByStudentAndFilter(studentId, filter);

        model.addAttribute("registrations", registrations);
        model.addAttribute("filter", filter);
        return "student/registrations/list"; // Tạo file: src/main/resources/templates/student/registrations/list.html
    }
}
