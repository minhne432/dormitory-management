package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROOMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "dorm_id", nullable = false)
    private Dormitory dormitory;

    @Column(name = "room_number", length = 50)
    private String roomNumber;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "current_occupancy")
    private Integer currentOccupancy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status; // 'available', 'full', 'maintenance'

    @Column(name = "description", length = 255)
    private String description;

    public enum RoomStatus {
        available, full, maintenance
    }

    // Constructors, getters, setters...
}
