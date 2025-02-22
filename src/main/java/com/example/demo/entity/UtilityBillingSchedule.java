package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utility_billing_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityBillingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    // Thời điểm hẹn giờ để tạo hóa đơn điện nước
    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    // Cờ đánh dấu lịch này chưa (true) hay đã (false) được xử lý
    @Column(name = "active", nullable = false)
    private Boolean active;
}
