package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomServiceUsageId implements Serializable {
    private Long roomId;
    private LocalDate recordDate;
    private String serviceName;
}
