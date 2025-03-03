package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;
import com.example.demo.entity.Student;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Gửi thông báo đến danh sách sinh viên
     * @param students Danh sách sinh viên
     * @param title Tiêu đề thông báo
     * @param message Nội dung thông báo
     */
    public void sendNotificationToStudents(List<Student> students, String title, String message) {
        for (Student student : students) {
            Notification notification = Notification.builder()
                    .student(student)
                    .title(title)
                    .message(message)
                    .createdAt(LocalDateTime.now())
                    .readStatus(Notification.ReadStatus.unread) // Mặc định là chưa đọc
                    .build();

            notificationRepository.save(notification);
        }
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        notification.setReadStatus(Notification.ReadStatus.read);
        notificationRepository.save(notification);
    }


    public List<NotificationDTO> getUnreadNotificationsByStudentId(Long studentId) {
        List<Notification> notifications = notificationRepository
                .findByStudent_StudentIdAndReadStatusOrderByCreatedAtDesc(studentId, Notification.ReadStatus.unread);

        return notifications.stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
