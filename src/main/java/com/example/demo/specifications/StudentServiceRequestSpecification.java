package com.example.demo.specifications;

import com.example.demo.entity.StudentServiceRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StudentServiceRequestSpecification {

    public static Specification<StudentServiceRequest> hasStudentId(Long studentId) {
        return (root, query, criteriaBuilder) -> {
            if (studentId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("studentId"), studentId);
        };
    }

    public static Specification<StudentServiceRequest> hasServiceStartDateBetween(LocalDate from, LocalDate to) {
        return (root, query, criteriaBuilder) -> {
            if (from == null && to == null) {
                return criteriaBuilder.conjunction();
            }
            if (from != null && to != null) {
                return criteriaBuilder.between(root.get("serviceStartDate"), from, to);
            } else if (from != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("serviceStartDate"), from);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("serviceStartDate"), to);
            }
        };
    }

    public static Specification<StudentServiceRequest> hasServiceName(String serviceName) {
        return (root, query, criteriaBuilder) -> {
            if (serviceName == null || serviceName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("serviceName")), "%" + serviceName.toLowerCase() + "%");
        };
    }
}
