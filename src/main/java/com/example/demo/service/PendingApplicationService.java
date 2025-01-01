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

    public Page<PendingApplication> getPendingApplications(String dormitoryArea, String address, String department, Pageable pageable) {
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

        return repository.findAll(spec, pageable);
    }
}
