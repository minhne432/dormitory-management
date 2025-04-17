package com.example.demo.controller;

import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.RoomAssignmentId;
import com.example.demo.service.RoomAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/room-assignments")
@RequiredArgsConstructor
public class RoomAssignmentManagementController {

    private final RoomAssignmentService raService;

    /** Danh sách sinh viên đang ở, lọc & phân trang */
    @GetMapping
    public String listAssignments(@RequestParam(required = false) Long studentId,
                                  @RequestParam(required = false) Long roomId,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        Page<RoomAssignment> assignments =
                raService.getActiveAssignments(studentId, roomId, page, size);

        model.addAttribute("assignments", assignments);
        model.addAttribute("studentId", studentId);
        model.addAttribute("roomId", roomId);
        return "manager/room_assignment/list";      // Thymeleaf view
    }

    /** Huỷ xếp phòng */
    @PostMapping("/cancel")
    public String cancel(@RequestParam Long studentId,
                         @RequestParam Long roomId,
                         RedirectAttributes redirectAttributes) {

        raService.cancelAssignment(new RoomAssignmentId(roomId, studentId));
        redirectAttributes.addFlashAttribute("successMessage", "Hủy xếp phòng thành công cho sinh viên mã: " +studentId);

        return "redirect:/manager/room-assignments";
    }

}
