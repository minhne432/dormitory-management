package com.example.demo.controller;

import com.example.demo.entity.StudentServiceRequest;
import com.example.demo.service.StudentServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/student-service-requests")
public class StudentServiceRequestManagementController {

    private final StudentServiceRequestService service;

    @Autowired
    public StudentServiceRequestManagementController(StudentServiceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public String listRequests(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String serviceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        LocalDate from = null;
        LocalDate to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if (fromDate != null && !fromDate.isEmpty()) {
                from = LocalDate.parse(fromDate, formatter);
            }
            if (toDate != null && !toDate.isEmpty()) {
                to = LocalDate.parse(toDate, formatter);
            }
        } catch (Exception e) {
            // Xử lý lỗi chuyển đổi định dạng ngày nếu cần
        }

        Page<StudentServiceRequest> result = service.getStudentServiceRequests(studentId, from, to, serviceName, PageRequest.of(page, size));
        model.addAttribute("result", result);
        model.addAttribute("studentId", studentId);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("serviceName", serviceName);
        return "manager/student_service_requests"; // Tên file template Thymeleaf
    }
}
