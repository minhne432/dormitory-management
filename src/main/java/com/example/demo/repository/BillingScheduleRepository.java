package com.example.demo.repository;

import com.example.demo.entity.BillingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface BillingScheduleRepository extends JpaRepository<BillingSchedule, Long> {
    List<BillingSchedule> findByScheduleTimeLessThanEqualAndActive(LocalDateTime now, Boolean active);
}
