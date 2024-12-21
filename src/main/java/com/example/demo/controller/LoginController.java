package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

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
    public String studentDashboard() {
        return "student/home"; // Trang chính sau khi student đăng nhập thành công
    }

    @GetMapping("/manager/home")
    public String managerDashboard() {
        return "manager/home"; // Trang chính sau khi manager đăng nhập thành công
    }
}
