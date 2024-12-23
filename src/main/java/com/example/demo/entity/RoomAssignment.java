package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ROOM_ASSIGNMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RoomAssignment {

    @EmbeddedId
    private RoomAssignmentId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Constructors, getters, setters...
}
