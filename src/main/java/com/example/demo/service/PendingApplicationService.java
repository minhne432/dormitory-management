// src/main/java/com/example/demo/service/PendingApplicationService.java
package com.example.demo.service;

import com.example.demo.entity.PendingApplication;
import com.example.demo.repository.PendingApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingApplicationService {

    @Autowired
    private PendingApplicationRepository repository;

    public Page<PendingApplication> getPendingApplications(String dormitoryArea, String address, String department, String applicationId, Pageable pageable) {
        Specification<PendingApplication> spec = Specification.where(null);

        if (dormitoryArea != null && !dormitoryArea.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("dormitoryArea"), dormitoryArea));
        }

        if (address != null && !address.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("address"), "%" + address + "%"));
        }

        if (department != null && !department.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("department"), department));
        }

        if (applicationId != null && !applicationId.isEmpty()) {
            try {
                long appId = Long.parseLong(applicationId);
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("applicationId"), appId));
            } catch (NumberFormatException e) {
                System.err.println("Invalid applicationId format: " + applicationId);
            }
        }

        // Thêm Sort mặc định theo submissionDate ASC nếu chưa có
        if (pageable.getSort().isUnsorted()) {
            pageable = org.springframework.data.domain.PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    org.springframework.data.domain.Sort.by("submissionDate").ascending()
            );
        }


        return repository.findAll(spec, pageable);
    }


}