package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "dashboard_summary_view")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummary {

    @Id
    @Column(name = "student_count")
    private Long studentCount; // giả định đây là ID duy nhất vì view chỉ trả về 1 dòng

    @Column(name = "available_rooms")
    private Long availableRooms;

    @Column(name = "pending_applications")
    private Long pendingApplications;

    @Column(name = "new_service_requests")
    private Long newServiceRequests;
}
