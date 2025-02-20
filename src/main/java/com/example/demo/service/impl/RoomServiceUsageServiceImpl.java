package com.example.demo.service.impl;

import com.example.demo.entity.RoomServiceUsage;
import com.example.demo.repository.RoomServiceUsageRepository;
import com.example.demo.service.RoomServiceUsageService;
import com.example.demo.specifications.RoomServiceUsageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RoomServiceUsageServiceImpl implements RoomServiceUsageService {

    private final RoomServiceUsageRepository repository;

    @Autowired
    public RoomServiceUsageServiceImpl(RoomServiceUsageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<RoomServiceUsage> getRoomServiceUsages(Long roomId, String roomNumber, LocalDate recordDate, String serviceName, Pageable pageable) {
        Specification<RoomServiceUsage> spec = Specification.where(RoomServiceUsageSpecification.hasRoomId(roomId))
                .and(RoomServiceUsageSpecification.hasRoomNumber(roomNumber))
                .and(RoomServiceUsageSpecification.hasRecordDate(recordDate))
                .and(RoomServiceUsageSpecification.hasServiceName(serviceName));
        return repository.findAll(spec, pageable);
    }
}
