package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    // Thời điểm hẹn giờ để tạo hóa đơn
    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    // Cờ đánh dấu lịch này có đang active (chưa xử lý) hay không
    @Column(name = "active", nullable = false)
    private Boolean active;

    // Nếu cần, bạn có thể thêm các trường khác (ví dụ: studentId nếu lịch riêng cho từng sinh viên)
    // private Long studentId;
}
