package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; // có thể null

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "read_status")
    private ReadStatus readStatus; // 'read','unread'

    public enum ReadStatus {
        read, unread
    }

    // Constructors, getters, setters...
}
