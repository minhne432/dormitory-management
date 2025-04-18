package com.example.demo.service;

import com.example.demo.entity.ServiceUsage;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public interface ServiceUsageService {
    ServiceUsage recordMeterReading(Long roomId, Long serviceId, Double currentReading, LocalDate recordDate);
    List<ServiceUsage> searchServiceUsages(Specification<ServiceUsage> spec);

    ServiceUsage getUsage(Long id);

    void updateCurrentReading(Long usageId, Double currentReading);

}
