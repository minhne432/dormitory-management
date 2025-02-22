package com.example.demo.repository;

import com.example.demo.entity.UtilityBillingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface UtilityBillingScheduleRepository extends JpaRepository<UtilityBillingSchedule, Long> {
    List<UtilityBillingSchedule> findByScheduleTimeLessThanEqualAndActive(LocalDateTime now, Boolean active);
}
