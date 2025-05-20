package com.example.demo.specifications;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Bill.BillStatus;
import com.example.demo.entity.Bill.BillType;
import com.example.demo.entity.Student;
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
            LocalDate endDate,
            LocalDate roomJoinDate      // tham số mới
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // --- 1. OR-predicates cho bill cá nhân hoặc bill phòng ---
            List<Predicate> orPreds = new ArrayList<>();
            if (studentId != null) {
                Join<Bill, Student> studentJoin = root.join("student", JoinType.LEFT);
                orPreds.add(cb.equal(studentJoin.get("studentId"), studentId));
            }
            if (roomId != null) {
                // bill phòng cơ bản
                Predicate roomBill = cb.and(
                        cb.equal(root.get("room").get("roomId"), roomId),
                        cb.isNull(root.get("student"))
                );
                // thêm điều kiện issueDate >= roomJoinDate nếu có
                if (roomJoinDate != null) {
                    roomBill = cb.and(
                            roomBill,
                            cb.greaterThanOrEqualTo(root.get("issueDate"), roomJoinDate)
                    );
                }
                orPreds.add(roomBill);
            }
            if (!orPreds.isEmpty()) {
                predicates.add(
                        orPreds.size() == 1
                                ? orPreds.get(0)
                                : cb.or(orPreds.toArray(new Predicate[0]))
                );
            }

            // --- 2. Lọc status ---
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // --- 3. Lọc billType ---
            if (billType != null) {
                predicates.add(cb.equal(root.get("billType"), billType));
            }

            // --- 4. Lọc khoảng thời gian chung (nếu có startDate/endDate) ---
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
