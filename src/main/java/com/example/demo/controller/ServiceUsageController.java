package com.example.demo.controller;

import com.example.demo.dto.RoomDto;
import com.example.demo.dto.ServiceUsageStatistic;
import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.Room;
import com.example.demo.entity.Dormitory;
import com.example.demo.entity.DormitoryService;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.DormitoryRepository;
import com.example.demo.repository.DormitoryServiceRepository;
import com.example.demo.service.ServiceUsageService;
import com.example.demo.service.UsageStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/service-usage")
public class ServiceUsageController {

    private final ServiceUsageService serviceUsageService;
    private final RoomRepository roomRepository;
    private final DormitoryRepository dormitoryRepository;
    private final DormitoryServiceRepository dormitoryServiceRepository;
    private final UsageStatisticService usageStatisticService;

    @Autowired
    public ServiceUsageController(ServiceUsageService serviceUsageService,
                                  RoomRepository roomRepository,
                                  DormitoryRepository dormitoryRepository,
                                  DormitoryServiceRepository dormitoryServiceRepository,
                                  UsageStatisticService usageStatisticService) {
        this.serviceUsageService = serviceUsageService;
        this.roomRepository = roomRepository;
        this.dormitoryRepository = dormitoryRepository;
        this.dormitoryServiceRepository = dormitoryServiceRepository;
        this.usageStatisticService = usageStatisticService;
    }

    // ✅ Hiển thị form ghi chỉ số (mở rộng: thêm lọc theo khu)
    @GetMapping("/record")
    public String showMeterReadingForm(Model model) {
        List<Dormitory> dormitories = dormitoryRepository.findAll();
        List<DormitoryService> services = dormitoryServiceRepository.findAll();

        model.addAttribute("dormitories", dormitories); // phục vụ cho dropdown chọn khu
        model.addAttribute("services", services);
        return "manager/record_meter_reading";
    }

    // ✅ API trả về danh sách phòng trong khu (dạng JSON cho AJAX)
    @GetMapping("/rooms")
    @ResponseBody
    public List<RoomDto> roomsByDorm(@RequestParam Long dormId) {
        return roomRepository
                .findByDormitoryDormIdAndCurrentOccupancyGreaterThan(dormId, 0)
                .stream()
                .map(r -> new RoomDto(r.getRoomId(), r.getRoomNumber()))
                .toList();
    }

    // ✅ Xử lý ghi chỉ số, dùng RedirectAttributes để báo lỗi/thành công
    @PostMapping("/record")
    public String recordMeterReading(@RequestParam Long roomId,
                                     @RequestParam Long serviceId,
                                     @RequestParam Double currentReading,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate,
                                     RedirectAttributes ra) {
        try {
            serviceUsageService.recordMeterReading(roomId, serviceId, currentReading, recordDate);
            ra.addFlashAttribute("success", "Ghi số thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/service-usage/record";
    }

    // ✅ Biểu đồ dịch vụ phòng
    @GetMapping("/room/chart")
    public String showRoomUsageChart(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model) {

        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        List<ServiceUsageStatistic> stats = usageStatisticService.getRoomUsageStatistics(startDate, endDate);
        model.addAttribute("chartData", stats);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "manager/service/room_usage_chart";
    }

    // ✅ Biểu đồ dịch vụ cá nhân
    @GetMapping("/personal/chart")
    public String showPersonalUsageChart(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model) {

        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        List<ServiceUsageStatistic> stats = usageStatisticService.getPersonalUsageStatistics(startDate, endDate);
        model.addAttribute("chartData", stats);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "manager/service/personal_usage_chart";
    }
}
