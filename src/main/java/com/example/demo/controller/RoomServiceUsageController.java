package com.example.demo.controller;

import com.example.demo.entity.RoomServiceUsage;
import com.example.demo.service.RoomServiceUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/room-service-usage")
public class RoomServiceUsageController {

    private final RoomServiceUsageService roomServiceUsageService;

    @Autowired
    public RoomServiceUsageController(RoomServiceUsageService roomServiceUsageService) {
        this.roomServiceUsageService = roomServiceUsageService;
    }

    @GetMapping
    public String listRoomServiceUsage(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String recordDate,
            @RequestParam(required = false) String serviceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        LocalDate parsedRecordDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if(recordDate != null && !recordDate.isEmpty()){
                parsedRecordDate = LocalDate.parse(recordDate, formatter);
            }
        } catch (Exception e) {
            // Nếu định dạng ngày không hợp lệ, có thể log lỗi hoặc bỏ qua
        }

        Page<RoomServiceUsage> result = roomServiceUsageService.getRoomServiceUsages(roomId, roomNumber, parsedRecordDate, serviceName, PageRequest.of(page, size));
        model.addAttribute("result", result);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("recordDate", recordDate);
        model.addAttribute("serviceName", serviceName);
        return "manager/room_service_usage";
    }
}
