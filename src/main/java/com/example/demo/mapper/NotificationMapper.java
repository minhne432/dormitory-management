package com.example.demo.mapper;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;
        return new NotificationDTO(
                notification.getNotificationId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getCreatedAt(),
                notification.getReadStatus().name() // chuyển enum -> string
        );
    }
}
