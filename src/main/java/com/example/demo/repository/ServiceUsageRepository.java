package com.example.demo.repository;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.Room;
import com.example.demo.entity.DormitoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface ServiceUsageRepository extends JpaRepository<ServiceUsage, Long> {
    Optional<ServiceUsage> findTopByRoomAndServiceOrderByRecordDateDesc(Room room, DormitoryService service);
    Optional<ServiceUsage> findByRoomAndServiceAndRecordDate(Room room, DormitoryService service, LocalDate recordDate);
}
