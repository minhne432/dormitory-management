package com.example.demo.controller;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.service.ServiceUsageService;
import com.example.demo.specifications.ServiceUsageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String roomNumber,    // ➊ thêm
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String invoiced,
            Model model) {

        LocalDate parsedStartDate = Optional.ofNullable(startDate)
                .filter(s -> !s.isBlank())
                .map(LocalDate::parse)
                .orElse(null);
        LocalDate parsedEndDate   = Optional.ofNullable(endDate)
                .filter(s -> !s.isBlank())
                .map(LocalDate::parse)
                .orElse(null);

        Specification<ServiceUsage> spec = ServiceUsageSpecification.hasRoomServiceType()
                .and(ServiceUsageSpecification.hasRoomId(roomId))
                .and(ServiceUsageSpecification.hasRoomNumber(roomNumber))     // ➋ thêm
                .and(ServiceUsageSpecification.hasRecordDateBetween(parsedStartDate, parsedEndDate))
                .and(ServiceUsageSpecification.hasInvoiced(invoiced));

        model.addAttribute("usages", serviceUsageService.searchServiceUsages(spec));
        return "manager/service_usage_list";
    }


    // ======== HIỂN THỊ FORM CHỈNH SỬA =========
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ServiceUsage usage = serviceUsageService.getUsage(id);

        if (usage.getInvoiced() == ServiceUsage.InvoicedStatus.YES) {
            return "redirect:/electricity-water-bill/list?error=locked";
        }

        model.addAttribute("usage", usage);
        return "manager/service_usage_form";
    }

    // ======== NHẬN FORM SUBMIT =========
    @PostMapping("/edit")
    public String update(
            @RequestParam("usageId") Long usageId,
            @RequestParam("currentReading") Double currentReading
    ) {
        // Chỉ cập nhật phần chỉ số mới
        serviceUsageService.updateCurrentReading(usageId, currentReading);
        return "redirect:/electricity-water-bill/list?success";
    }

}
