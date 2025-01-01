package com.example.demo.controller;

import com.example.demo.entity.PendingApplication;
import com.example.demo.service.PendingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class PendingApplicationController {

    @Autowired
    private PendingApplicationService service;

    @GetMapping("/manager/applications/pending-applications")
    public String getPendingApplications(
            Model model,
            String dormitoryArea,
            String address,
            String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        // Sử dụng đúng Pageable từ Spring Data
        Pageable pageable = PageRequest.of(page, size);

        // Gọi service để lấy dữ liệu phân trang
        Page<PendingApplication> applicationPage = service.getPendingApplications(dormitoryArea, address, department, pageable);

        // Thêm dữ liệu vào model
        model.addAttribute("applicationPage", applicationPage);
        model.addAttribute("dormitoryArea", dormitoryArea);
        model.addAttribute("address", address);
        model.addAttribute("department", department);

        // Thêm danh sách mẫu cho dropdown
        List<String> dormitoryAreas = Arrays.asList("Khu A", "Khu B");
        List<String> departments = Arrays.asList("Computer Science", "Electrical Engineering");
        model.addAttribute("dormitoryAreas", dormitoryAreas);
        model.addAttribute("departments", departments);

        return "manager/application/pending_applications";
    }
}
