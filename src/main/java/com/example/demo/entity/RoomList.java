package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_list") // Tên view
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RoomList {

    @Id
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_description")
    private String roomDescription;

    @Column(name = "status") // Sửa tên cột từ "room_status" thành "status"
    private String roomStatus;

    @Column(name = "current_occupancy")
    private Integer currentOccupancy;

    @Column(name = "room_max_capacity")
    private Integer roomMaxCapacity;

    @Column(name = "room_type_name") // Sửa tên cột từ "room_type" thành "room_type_name"
    private String roomTypeName;

    @Column(name = "room_type_price") // Sửa tên cột từ "room_price" thành "room_type_price"
    private Double roomTypePrice;

    @Column(name = "dorm_name")
    private String dormName;

    @Column(name = "dorm_address")
    private String dormAddress;

    @Column(name = "dorm_description") // Thêm trường cho cột "dorm_description"
    private String dormDescription;

    @Column(name = "room_type_id") // Thêm trường cho cột "room_type_id"
    private Long roomTypeId;

    @Column(name = "dorm_id") // Thêm trường cho cột "dorm_id"
    private Long dormId;
}
