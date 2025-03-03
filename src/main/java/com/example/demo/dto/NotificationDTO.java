package com.example.demo.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long notificationId;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String readStatus;

    // Constructors
    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationId, String title, String message, LocalDateTime createdAt, String readStatus) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readStatus = readStatus;
    }

    // Getters and setters
    public Long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReadStatus() {
        return readStatus;
    }
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
