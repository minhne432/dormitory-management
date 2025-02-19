package com.example.demo.specifications;

import com.example.demo.entity.Bill;
import org.springframework.data.jpa.domain.Specification;

public class BillSpecification {
    public static Specification<Bill> hasStudentId(Long studentId) {
        return (root, query, criteriaBuilder) -> {
            if(studentId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("student").get("studentId"), studentId);
        };
    }
}
