package com.example.demo.repository;

import com.example.demo.entity.RoomServiceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomServiceUsageRepository extends JpaRepository<RoomServiceUsage, Long>, JpaSpecificationExecutor<RoomServiceUsage> {
}
