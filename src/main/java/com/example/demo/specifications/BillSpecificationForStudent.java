package com.example.demo.specifications;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Bill.BillStatus;
import com.example.demo.entity.Bill.BillType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillSpecificationForStudent {

    public static Specification<Bill> filter(
            Long studentId,
            BillStatus status,
            BillType billType,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo studentId
            if (studentId != null) {
                // student là ManyToOne, nên ta join tới student -> studentId
                predicates.add(
                        cb.equal(root.join("student").get("studentId"), studentId)
                );
            }

            // Lọc theo BillStatus
            if (status != null) {
                predicates.add(
                        cb.equal(root.get("status"), status)
                );
            }

            // Lọc theo BillType
            if (billType != null) {
                predicates.add(
                        cb.equal(root.get("billType"), billType)
                );
            }

            // Lọc theo khoảng thời gian của issueDate
            if (startDate != null && endDate != null) {
                predicates.add(
                        cb.between(root.get("issueDate"), startDate, endDate)
                );
            } else if (startDate != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("issueDate"), startDate)
                );
            } else if (endDate != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("issueDate"), endDate)
                );
            }

            // Gom tất cả predicate lại bằng AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
