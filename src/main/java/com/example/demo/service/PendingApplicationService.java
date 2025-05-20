// src/main/java/com/example/demo/service/PendingApplicationService.java
package com.example.demo.service;

import com.example.demo.entity.PendingApplication;
import com.example.demo.repository.PendingApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PendingApplicationService {

    @Autowired
    private PendingApplicationRepository repository;

    public Page<PendingApplication> getPendingApplications(
            String dormitoryArea,
            String address,
            String department,
            String applicationId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Specification<PendingApplication> spec = Specification.where(null);

        // các filter cũ...
        if (applicationId != null && !applicationId.isEmpty()) {
            // parse và filter theo applicationId
        }

        // **Filter theo khoảng ngày nộp**
        if (startDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("submissionDate"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("submissionDate"), endDate));
        }

        // nếu chưa sort, ta mặc định sort theo submissionDate
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("submissionDate").ascending()
            );
        }

        return repository.findAll(spec, pageable);
    }


}