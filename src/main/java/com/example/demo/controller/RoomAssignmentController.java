package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.entity.Application.ApplicationStatus;
import com.example.demo.entity.Room.RoomStatus;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.RoomAssignmentRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manager/room-assignments")
public class RoomAssignmentController {

    private final ApplicationRepository applicationRepository;
    private final RoomRepository roomRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;

    public RoomAssignmentController(ApplicationRepository applicationRepository,
                                    RoomRepository roomRepository,
                                    RoomAssignmentRepository roomAssignmentRepository) {
        this.applicationRepository = applicationRepository;
        this.roomRepository = roomRepository;
        this.roomAssignmentRepository = roomAssignmentRepository;
    }

    /**
     * Liệt kê danh sách Application đã approved nhưng chưa xếp phòng
     */
    @GetMapping
    public String listApprovedApplications(Model model) {
        // Lấy tất cả application status=approved
        List<Application> approvedApps = applicationRepository.findByStatus(ApplicationStatus.approved);

        // Lọc những student chưa có RoomAssignment (nếu bạn muốn)
        // Ở đây tuỳ ý: hiển thị tất cả, hoặc check logic
        // ...

        model.addAttribute("approvedApps", approvedApps);
        return "manager/assignment/listApproved";
        // -> Tạo file: resources/templates/manager/assignment/listApproved.html
    }

    /**
     * Hiển thị form chọn phòng cho 1 application cụ thể
     * URL: GET /manager/room-assignments/{appId}/assign
     */
    @GetMapping("/{appId}/assign")
    public String showAssignForm(@PathVariable Long appId, Model model) {
        // Lấy application
        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID:" + appId));

        // Lấy danh sách phòng còn trống => status=available, current < max
        List<Room> availableRooms = roomRepository.findByStatusAndCurrentOccupancyLessThan(
                RoomStatus.available, 99_999 // Hoặc so sánh maxCapacity, tuỳ logic
        );

        model.addAttribute("applicationDetails", application);
        model.addAttribute("availableRooms", availableRooms);

        return "manager/assignment/assignForm";
        // -> Tạo file Thymeleaf: assignForm.html
    }

    /**
     * Xử lý POST xếp phòng
     * URL: POST /manager/room-assignments/{appId}/assign
     */

    @PostMapping("/{appId}/assign")
    public String assignRoom(@PathVariable Long appId,
                             @RequestParam("roomId") Long roomId) {
        // Lấy Application
        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID:" + appId));

        Student student = application.getStudent();

        // Lấy Room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID:" + roomId));

        // Kiểm tra sức chứa
        if (room.getCurrentOccupancy() >= room.getMaxCapacity()) {
            throw new IllegalStateException("Phòng đã đầy!");
        }

        // Tạo RoomAssignmentId
        RoomAssignmentId roomAssignmentId = new RoomAssignmentId(room.getRoomId(), student.getStudentId());

        // Tạo RoomAssignment
        RoomAssignment assignment = new RoomAssignment();
        assignment.setId(roomAssignmentId); // Đặt EmbeddedId
        assignment.setRoom(room);
        assignment.setStudent(student);
        assignment.setAssignedDate(LocalDate.now());
        // end_date null => Chưa có

        // Lưu RoomAssignment
        roomAssignmentRepository.save(assignment);

        // Tăng occupancy
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        if (room.getCurrentOccupancy().equals(room.getMaxCapacity())) {
            room.setStatus(RoomStatus.full); // Nếu đã full => cập nhật
        }
        roomRepository.save(room);

        // (Tuỳ ý) Cập nhật application -> note = "Đã xếp phòng" hay thay đổi gì đó
        // application.setNote("Đã xếp phòng " + room.getRoomNumber());
        // applicationRepository.save(application);

        return "redirect:/manager/room-assignments?success=assigned";
    }

}
