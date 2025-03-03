package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;


    // Ví dụ: Lấy danh sách thông báo chưa đọc của một student theo studentId
    @GetMapping("/poll")
    @ResponseBody
    public List<NotificationDTO> pollNotifications(@RequestParam Long studentId) {
        // Lấy danh sách thông báo chưa đọc
        List<Notification> unreadNotifications = notificationRepository
                .findByStudent_StudentIdAndReadStatusOrderByCreatedAtDesc(studentId, Notification.ReadStatus.unread);

        // Chuyển đổi sang DTO để tránh vòng lặp
        return unreadNotifications.stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }


    // Lấy danh sách thông báo của 1 student
    @GetMapping("/student/{studentId}")
    public String getStudentNotifications(@PathVariable Long studentId, Model model) {
        List<Notification> notifications = notificationRepository.findByStudent_StudentIdOrderByCreatedAtDesc(studentId);
        model.addAttribute("notifications", notifications);
        return "notification/list"; // Tạo file notification/list.html
    }

    // Đánh dấu thông báo đã đọc
    @PostMapping("/read/{notificationId}")
    public String markAsRead(@PathVariable Long notificationId) {
        Notification notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setReadStatus(Notification.ReadStatus.read);
        notificationRepository.save(notif);
        // Chuyển hướng về trang thông báo
        return "redirect:/notifications/student/" + notif.getStudent().getStudentId();
    }

    @GetMapping("/list")
    public String getNotificationsForStudent(@RequestParam("studentId") Long studentId, Model model) {
        List<Notification> notifications = notificationRepository.findByStudent_StudentIdOrderByCreatedAtDesc(studentId);
        model.addAttribute("notifications", notifications);
        return "notification/list";
    }

}
