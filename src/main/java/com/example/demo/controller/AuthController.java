package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // GET /register -> Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";  // trả về template Thymeleaf: register.html
    }

    // POST /register -> Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               Model model) {
        try {
            if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                model.addAttribute("error", "Password and Confirm Password do not match!");
                return "register";
            }
            authService.registerNewUser(registerRequest);
            return "redirect:/login?registerSuccess=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
