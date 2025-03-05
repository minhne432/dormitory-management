package com.example.demo.controller;

import com.example.demo.entity.Dormitory;
import com.example.demo.entity.PendingApplication;
import com.example.demo.service.DormitoryService;
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

    @Autowired
    private DormitoryService dormitoryService;

    @GetMapping("/manager/applications/pending-applications")
    public String getPendingApplications(
            Model model,
            String dormitoryArea,
            String address,
            String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // Giả sử service trả về Page<PendingApplication> có trường dormitory
        Page<PendingApplication> applicationPage = service.getPendingApplications(dormitoryArea, address, department, pageable);

        // Thêm dữ liệu vào model
        model.addAttribute("applicationPage", applicationPage);
        model.addAttribute("dormitoryArea", dormitoryArea);
        model.addAttribute("address", address);
        model.addAttribute("department", department);

        // Lấy danh sách các Dormitory từ service
        List<Dormitory> dormitoryAreas = dormitoryService.getAllDormitories();
        model.addAttribute("dormitoryAreas", dormitoryAreas);

        // Thêm danh sách mẫu cho dropdown
        List<String> departments = Arrays.asList("Computer Science", "Electrical Engineering");
        model.addAttribute("departments", departments);

        return "manager/application/pending_applications";
    }

}
