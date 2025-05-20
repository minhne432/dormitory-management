package com.example.demo.repository;

import com.example.demo.entity.DashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardSummaryRepository extends JpaRepository<DashboardSummary, Long> {
}
