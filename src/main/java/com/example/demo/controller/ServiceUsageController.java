package com.example.demo.controller;

import com.example.demo.dto.ServiceUsageStatistic;
import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.Room;
import com.example.demo.entity.DormitoryService;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.DormitoryServiceRepository;
import com.example.demo.service.ServiceUsageService;
import com.example.demo.service.UsageStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/service-usage")
public class ServiceUsageController {

    private final ServiceUsageService serviceUsageService;
    private final RoomRepository roomRepository;
    private final DormitoryServiceRepository dormitoryServiceRepository;

    private final UsageStatisticService usageStatisticService;

    @Autowired
    public ServiceUsageController(ServiceUsageService serviceUsageService,
                                  RoomRepository roomRepository,
                                  DormitoryServiceRepository dormitoryServiceRepository,
                                  UsageStatisticService usageStatisticService) {
        this.serviceUsageService = serviceUsageService;
        this.roomRepository = roomRepository;
        this.dormitoryServiceRepository = dormitoryServiceRepository;
        this.usageStatisticService = usageStatisticService;
    }

    // Hiển thị form ghi số điện/nước
    @GetMapping("/record")
    public String showMeterReadingForm(Model model) {
List<Room> rooms = roomRepository.findAll().stream()
                .filter(room -> room.getCurrentOccupancy() > 0) // Lọc ra các phòng đã có người ở
                .toList();
        List<DormitoryService> services = dormitoryServiceRepository.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("services", services);
        return "manager/record_meter_reading";
    }

    // Xử lý dữ liệu từ form
    @PostMapping("/record")
    public String recordMeterReading(@RequestParam("roomId") Long roomId,
                                     @RequestParam("serviceId") Long serviceId,
                                     @RequestParam("currentReading") Double currentReading,
                                     @RequestParam("recordDate") String recordDateStr,
                                     Model model) {
        try {
            LocalDate recordDate = LocalDate.parse(recordDateStr); // định dạng yyyy-MM-dd
            ServiceUsage usage = serviceUsageService.recordMeterReading(roomId, serviceId, currentReading, recordDate);
            model.addAttribute("usage", usage);
            return "manager/record_meter_reading_success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/record_meter_reading";
        }
    }

    // Biểu đồ dịch vụ phòng
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

    // Biểu đồ dịch vụ cá nhân
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
