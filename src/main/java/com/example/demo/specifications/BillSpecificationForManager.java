package com.example.demo.specifications;

import com.example.demo.entity.Bill;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;

public class BillSpecificationForManager {

    public static Specification<Bill> filter(Long billId, Long studentId, Bill.BillStatus status, Bill.BillType billType,
                                             LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (billId != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("billId"), billId));
            }

            if (studentId != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("student").get("studentId"), studentId));
            }

            if (status != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("status"), status));
            }

            if (billType != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("billType"), billType));
            }

            if (startDate != null && endDate != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.between(root.get("issueDate"), startDate, endDate));
            } else if (startDate != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("issueDate"), startDate));
            } else if (endDate != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("issueDate"), endDate));
            }

            return predicate;
        };
    }
}
