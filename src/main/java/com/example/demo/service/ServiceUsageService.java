package com.example.demo.service;

import com.example.demo.entity.ServiceUsage;
import java.time.LocalDate;

public interface ServiceUsageService {
    ServiceUsage recordMeterReading(Long roomId, Long serviceId, Double currentReading, LocalDate recordDate);
}
