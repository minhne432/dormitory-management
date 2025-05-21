package com.example.demo.controller;

import com.example.demo.entity.Dormitory;
import com.example.demo.entity.PendingApplication;
import com.example.demo.service.DormitoryService;
import com.example.demo.service.PendingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
            String applicationId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PendingApplication> applicationPage = service.getPendingApplications(
                dormitoryArea, address, department, applicationId, startDate, endDate, pageable);

        // đẩy lại các giá trị lên view
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("dormitoryArea", dormitoryArea);
        model.addAttribute("address", address);
        model.addAttribute("department", department);
        model.addAttribute("applicationId", applicationId);

        model.addAttribute("dormitoryAreas", dormitoryService.getAllDormitories());
        model.addAttribute("departments", Arrays.asList(
                "CNTT", "KinhTe", "XHNV",
                "BK",      // Trường Bách khoa
                "SP",      // Trường Sư phạm
                "TS",      // Trường Thủy sản
                "NN",      // Trường Nông nghiệp
                "DBDT",    // Khoa Dự bị Dân tộc
                "GDTC",    // Khoa Giáo dục Thể chất
                "KHCT",    // Khoa Khoa học Chính trị
                "KHTN",    // Khoa Khoa học Tự nhiên
                "LU",      // Khoa Luật
                "MT&TNTN", // Khoa Môi trường & Tài nguyên Thiên nhiên
                "PTNT",    // Khoa Phát triển Nông thôn
                "SDH"      // Khoa Sau đại học
        ));

        model.addAttribute("applicationPage", applicationPage);
        return "manager/application/pending_applications";
    }

}
