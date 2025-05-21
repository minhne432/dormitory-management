package com.example.demo.controller;

import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import com.example.demo.service.RoomAssignmentService;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/assignments")
public class RoomAssignmentUIController {

    @Autowired private RoomAssignmentService     roomAssignmentService;
    @Autowired private RoomRepository           roomRepo;
    @Autowired private StudentRepository        studentRepo;

    /** 1. Trang list + filter */
    /** 1. Trang list + filter (chỉ show assignment chưa có endDate) */
    @GetMapping
    public String list(
            @RequestParam Optional<Long> roomId,
            @RequestParam Optional<Long> studentId,
            Model model) {

        // 1. Chuẩn bị dữ liệu dropdown
        model.addAttribute("rooms",    roomRepo.findAll());
        model.addAttribute("students", studentRepo.findAll());

        // 2. Lấy tất cả assignment theo filter phòng & sinh viên
        List<RoomAssignment> all = roomAssignmentService.list(
                roomId.orElse(null),
                studentId.orElse(null)
        );

        // 3. Lọc chỉ giữ những assignment chưa có endDate
        List<RoomAssignment> active = all.stream()
                .filter(ra -> ra.getEndDate() == null)
                .collect(Collectors.toList());

        // 4. Đưa vào Model
        model.addAttribute("assignments", active);

        // 5. Map lưu filter (để Thymeleaf giữ giá trị select)
        Map<String,Object> filter = new HashMap<>();
        roomId.ifPresent(id -> filter.put("roomId", id));
        studentId.ifPresent(id -> filter.put("studentId", id));
        model.addAttribute("filter", filter);

        return "manager/room_assignments";
    }

    /** 2. Hiển thị form chuyển phòng */
    @GetMapping("/transfer/{studentId}/{roomId}")
    public String showTransferForm(
            @PathVariable Long studentId,
            @PathVariable Long roomId,
            Model model) {

        RoomAssignment current = roomAssignmentService.getById(studentId, roomId);
        model.addAttribute("current", current);
        model.addAttribute("rooms",   roomRepo.findAll());
        return "manager/transfer_room";
    }

    /** 3. Xử lý chuyển phòng */
    @PostMapping("/transfer")
    public String doTransfer(
            @RequestParam Long studentId,
            @RequestParam Long oldRoomId,
            @RequestParam Long newRoomId) {

        roomAssignmentService.transferRoom(studentId, oldRoomId, newRoomId);
        return "redirect:/assignments";
    }

    /** 4. Dừng ở (hủy phân công) */
    @GetMapping("/end/{studentId}/{roomId}")
    public String cancelAssignment(
            @PathVariable Long studentId,
            @PathVariable Long roomId) {

        RoomAssignmentId id = new RoomAssignmentId(roomId, studentId);
        roomAssignmentService.cancelAssignment(id);
        return "redirect:/assignments";
    }
}
