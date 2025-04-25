package com.example.demo.controller;

import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    // ⬅️ Truy cập form tại: /mail/form
    @GetMapping("/form")
    public String showForm(Model model) {
        return "notification/send-email"; // HTML form nằm ở: templates/notification/send-email.html
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body,
                            Model model) {
        try {
            emailService.sendSimpleEmail(to, subject, body);
            model.addAttribute("message", "Email sent successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send email: " + e.getMessage());
        }
        return "notification/email-result"; // điều hướng đến HTML
    }
}
