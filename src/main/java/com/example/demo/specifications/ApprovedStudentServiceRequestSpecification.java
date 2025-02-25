package com.example.demo.specifications;

import com.example.demo.entity.ApprovedStudentServiceRequest;
import com.example.demo.dto.ApprovedStudentServiceRequestFilter;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ApprovedStudentServiceRequestSpecification {
    public static Specification<ApprovedStudentServiceRequest> filter(ApprovedStudentServiceRequestFilter filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getRegistrationId() != null) {
                predicates.add(builder.equal(root.get("registrationId"), filter.getRegistrationId()));
            }
            if (filter.getStudentId() != null) {
                predicates.add(builder.equal(root.get("studentId"), filter.getStudentId()));
            }
            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()) {
                predicates.add(builder.like(root.get("phoneNumber"), "%" + filter.getPhoneNumber() + "%"));
            }
            if (filter.getRoomId() != null) {
                predicates.add(builder.equal(root.get("roomId"), filter.getRoomId()));
            }
            if (filter.getServiceStartDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("serviceStartDate"), filter.getServiceStartDate()));
            }
            if (filter.getServiceEndDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("serviceEndDate"), filter.getServiceEndDate()));
            }
            if (filter.getServiceName() != null && !filter.getServiceName().isEmpty()) {
                predicates.add(builder.like(root.get("serviceName"), "%" + filter.getServiceName() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
