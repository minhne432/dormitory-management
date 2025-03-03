package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;

    @Autowired
    public LoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "registerSuccess", required = false) String registerSuccess
    ) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }
        if (registerSuccess != null) {
            model.addAttribute("message", "Register successfully, please login!");
        }
        return "login"; // login.html
    }

    @GetMapping("/student/home")
    public String studentDashboard(HttpSession session, Principal principal) {  // Thêm HttpSession
        // 1) Lấy username (hoặc email) từ principal
        String username = principal.getName();

        // 2) Tìm đối tượng Student qua repository (hoặc service)
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 3) Đưa student vào session
        session.setAttribute("student", student);

        // 4) Không cần add vào model nữa nếu chỉ dùng ở sidebar
        // model.addAttribute("student", student); // Có thể bỏ dòng này

        // 5) Trả về view
        return "student/home";
    }

    @GetMapping("/manager/home")
    public String managerDashboard() {
        return "manager/home"; // Trang chính sau khi manager đăng nhập thành công
    }
}
