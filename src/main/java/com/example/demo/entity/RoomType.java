package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROOM_TYPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Long roomTypeId;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity; // Số lượng người tối đa của phòng

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "dorm_id", nullable = false)
    private Dormitory dormitory;

    // Thêm trường phân loại theo giới tính: ENUM('Nam','Nữ')
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "ENUM('Nam','Nữ')")
    private Gender gender;

    public enum Gender {
        Nam, Nữ
    }
}
