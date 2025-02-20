package com.example.demo.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Immutable // Đánh dấu đây là một View (chỉ đọc)
@Entity
@Table(name = "room_service_usage") // Ánh xạ đến view trong MySQL
@Subselect("SELECT * FROM room_service_usage") // Hibernate sẽ không tạo bảng này
public class RoomServiceUsage {

    @EmbeddedId
    private RoomServiceUsageId id;

    @Column(name = "room_number", length = 50)
    private String roomNumber;

    @Column(name = "unit", length = 255)
    private String unit;

    @Column(name = "previous_reading")
    private Double previousReading;

    @Column(name = "current_reading")
    private Double currentReading;

    @Column(name = "consumption")
    private Double consumption;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "\"service_name\"", insertable = false, updatable = false)
    private String serviceName;

}
