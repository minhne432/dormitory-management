package com.example.demo.controller;

import com.example.demo.entity.BillingSchedule;
import com.example.demo.repository.BillingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/billing-schedule")
public class BillingScheduleController {

    @Autowired
    private BillingScheduleRepository billingScheduleRepository;

    // Hiển thị form nhập lịch hẹn hóa đơn
    @GetMapping("/new")
    public String showCreateBillingScheduleForm(Model model) {
        // Khởi tạo một đối tượng BillingSchedule với active mặc định là true và scheduleTime là thời gian hiện tại
        BillingSchedule schedule = BillingSchedule.builder()
                .active(true)
                .scheduleTime(LocalDateTime.now())
                .build();
        model.addAttribute("schedule", schedule);
        return "manager/billing/new_billing_schedule";
    }

    // Xử lý lưu lịch hẹn hóa đơn
    @PostMapping("/save")
    public String saveBillingSchedule(@ModelAttribute BillingSchedule schedule) {
        billingScheduleRepository.save(schedule);
        // Sau khi lưu, bạn có thể chuyển hướng đến trang liệt kê lịch nếu có
        return "redirect:/billing-schedule/list";
    }

    // (Tuỳ chọn) Hiển thị danh sách lịch hẹn để manager theo dõi
    @GetMapping("/list")
    public String listBillingSchedules(Model model) {
        model.addAttribute("schedules", billingScheduleRepository.findAll());
        return "manager/billing/list_billing_schedules";
    }
}
