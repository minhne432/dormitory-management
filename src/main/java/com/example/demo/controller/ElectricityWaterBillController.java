package com.example.demo.controller;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.service.ServiceUsageService;
import com.example.demo.specifications.ServiceUsageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/electricity-water-bill")
public class ElectricityWaterBillController {

    private final ServiceUsageService serviceUsageService;

    @Autowired
    public ElectricityWaterBillController(ServiceUsageService serviceUsageService) {
        this.serviceUsageService = serviceUsageService;
    }

    @GetMapping("/list")
    public String listServiceUsage(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Double currentReading,
            @RequestParam(required = false) Double previousReading,
            @RequestParam(required = false) String recordDate,
            @RequestParam(required = false) String invoiced,
            Model model) {

        // Chuyển đổi recordDate an toàn để tránh lỗi định dạng
        LocalDate parsedRecordDate = Optional.ofNullable(recordDate)
                .filter(date -> !date.isBlank())
                .map(LocalDate::parse)
                .orElse(null);

        // Xây dựng Specification theo thứ tự: mặc định có serviceType ROOM + các bộ lọc nếu có
        Specification<ServiceUsage> spec = ServiceUsageSpecification.hasRoomServiceType()
                .and(ServiceUsageSpecification.hasRoomId(roomId))
                .and(ServiceUsageSpecification.hasCurrentReading(currentReading))
                .and(ServiceUsageSpecification.hasPreviousReading(previousReading))
                .and(ServiceUsageSpecification.hasRecordDate(recordDate))
                .and(ServiceUsageSpecification.hasInvoiced(invoiced));

        model.addAttribute("usages", serviceUsageService.searchServiceUsages(spec));
        return "manager/service_usage_list";
    }
}
