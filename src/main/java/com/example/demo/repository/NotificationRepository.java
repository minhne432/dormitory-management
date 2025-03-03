package com.example.demo.repository;

import com.example.demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Ví dụ: lấy danh sách thông báo của 1 student, sắp xếp theo thời gian gần nhất

    List<Notification> findByStudent_StudentIdOrderByCreatedAtDesc(Long studentId);

    List<Notification> findByStudent_StudentIdAndReadStatusOrderByCreatedAtDesc(
            Long studentId,
            Notification.ReadStatus readStatus
    );


}
