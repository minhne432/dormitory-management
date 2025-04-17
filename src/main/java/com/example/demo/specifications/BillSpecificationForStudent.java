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
            Long roomId,
            BillStatus status,
            BillType billType,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ✅ Lọc hóa đơn cá nhân hoặc hóa đơn phòng
            if (studentId != null && roomId != null) {
                // Join student với LEFT JOIN vì hóa đơn phòng có thể null student
                Join<Object, Object> studentJoin = root.join("student", JoinType.LEFT);

                Predicate personalBill = cb.equal(studentJoin.get("studentId"), studentId);
                Predicate roomBill = cb.and(
                        cb.equal(root.get("room").get("roomId"), roomId),
                        cb.isNull(root.get("student")) // hóa đơn phòng
                );

                predicates.add(cb.or(personalBill, roomBill));
            }

            // Lọc theo BillStatus
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // Lọc theo BillType
            if (billType != null) {
                predicates.add(cb.equal(root.get("billType"), billType));
            }

            // Lọc theo khoảng thời gian của issueDate
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("issueDate"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("issueDate"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("issueDate"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
