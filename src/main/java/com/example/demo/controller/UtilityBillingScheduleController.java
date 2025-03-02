package com.example.demo.controller;

import com.example.demo.entity.UtilityBillingSchedule;
import com.example.demo.repository.UtilityBillingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/utility-billing-schedule")
public class UtilityBillingScheduleController {

    @Autowired
    private UtilityBillingScheduleRepository utilityBillingScheduleRepository;

    // Hiển thị form nhập lịch hẹn hóa đơn điện/nước
    @GetMapping("/new")
    public String showCreateUtilityBillingScheduleForm(Model model) {
        // Tạo đối tượng UtilityBillingSchedule mặc định
        UtilityBillingSchedule schedule = UtilityBillingSchedule.builder()
                .active(true)
                .scheduleTime(LocalDateTime.now())
                .build();

        model.addAttribute("schedule", schedule);
        return "manager/billing/new_utility_billing_schedule";
    }

    // Xử lý lưu lịch hẹn hóa đơn tiện ích
    @PostMapping("/save")
    public String saveUtilityBillingSchedule(@ModelAttribute UtilityBillingSchedule schedule) {
        // Lưu vào CSDL
        utilityBillingScheduleRepository.save(schedule);
        // Tùy thuộc vào logic, có thể chuyển hướng về danh sách schedule
        return "redirect:/utility-billing-schedule/list";
    }

    // (Tuỳ chọn) Hiển thị danh sách lịch hẹn để manager theo dõi
    @GetMapping("/list")
    public String listUtilityBillingSchedules(Model model) {
        model.addAttribute("schedules", utilityBillingScheduleRepository.findAll());
        return "manager/billing/list_utility_billing_schedules";
    }
}
