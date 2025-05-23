package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DormitoryService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String serviceName;
    private Double unitPrice;
    private String unit;
    private String description;

    @Enumerated(EnumType.STRING) // Chỉ định kiểu ENUM
    private ServiceType serviceType; // Thêm trường mới

    @Column(columnDefinition = "boolean default true") // Mặc định là true
    private Boolean visibleForStudent; // Trường xác định có hiển thị với sinh viên hay không

    // Enum để định nghĩa các loại dịch vụ
    public enum ServiceType {
        PERSONAL,
        ROOM
    }
}
