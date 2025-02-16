package com.example.demo.controller;

import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.service.StudentServiceRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student-service-registrations")
public class StudentServiceRegistrationController {

    private final StudentServiceRegistrationService registrationService;

    @Autowired
    public StudentServiceRegistrationController(StudentServiceRegistrationService registrationService) {
        this.registrationService = registrationService;
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
}
