package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/profile")
    public String viewProfile(Model model, Authentication authentication) {
        // Lấy username từ đối tượng Authentication (điều này hoạt động khi sử dụng Spring Security)
        String username = authentication.getName();

        // Lấy đối tượng Student từ service
        Student student = studentService.getStudentByUsername(username);

        // Đưa đối tượng student vào model để view sử dụng
        model.addAttribute("student", student);

        // Trả về view "student/profile.html"
        return "student/profile";
    }
}
