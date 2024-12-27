package com.example.demo.repository;

import com.example.demo.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    /**
     * Tìm tất cả Application có submissionDate nằm trong [startDate, endDate].
     * Kết quả trả về dạng Page, dùng cho phân trang.
     */
    Page<Application> findBySubmissionDateBetweenAndStatus(LocalDate startDate, LocalDate endDate,Application.ApplicationStatus status ,Pageable pageable);
    List<Application> findByStatus(Application.ApplicationStatus status);

}
