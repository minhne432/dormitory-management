package com.example.demo.dto;

import com.example.demo.entity.Room.RoomStatus;
import lombok.Data;

@Data
public class RoomFilter {
    private String roomNumber;
    private String dormName;
    private Integer maxCapacity;   // Lọc theo sức chứa
    private String gender;         // Lọc theo giới tính (Nam / Nữ)
    private Double price;          // Lọc theo giá phòng
    private RoomStatus status;     // available / full / maintenance
}
