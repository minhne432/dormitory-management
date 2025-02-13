package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ServiceUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long usageId;

    @Column(name = "current_reading")
    private Double currentReading;

    @Column(name = "previous_reading")
    private Double previousReading;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoiced", columnDefinition = "ENUM('NO','YES') NOT NULL")
    private InvoicedStatus invoiced;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DormitoryService service;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public enum InvoicedStatus {
        NO, YES
    }
}
