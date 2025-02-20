package com.example.demo.service;

import com.example.demo.entity.RoomServiceUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RoomServiceUsageService {
    Page<RoomServiceUsage> getRoomServiceUsages(Long roomId, String roomNumber, LocalDate recordDate, String serviceName, Pageable pageable);
}
