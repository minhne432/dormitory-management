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
            LocalDate endDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Tạo danh sách OR-predicates cho bill cá nhân và bill phòng
            List<Predicate> orPreds = new ArrayList<>();
            // Nếu có studentId thì thêm predicate lọc bill cá nhân
            if (studentId != null) {
                Join<Bill, Student> studentJoin = root.join("student", JoinType.LEFT);
                orPreds.add(cb.equal(studentJoin.get("studentId"), studentId));
            }
            // Nếu có roomId thì thêm predicate lọc bill phòng (student == null)
            if (roomId != null) {
                orPreds.add(cb.and(
                        cb.equal(root.get("room").get("roomId"), roomId),
                        cb.isNull(root.get("student"))
                ));
            }
            // Nếu có bất kỳ predicate nào thì gộp lại bằng OR
            if (!orPreds.isEmpty()) {
                predicates.add(orPreds.size() == 1
                        ? orPreds.get(0)
                        : cb.or(orPreds.toArray(new Predicate[0])));
            }

            // 2. Lọc theo status nếu có
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // 3. Lọc theo billType nếu có
            if (billType != null) {
                predicates.add(cb.equal(root.get("billType"), billType));
            }

            // 4. Lọc theo khoảng thời gian
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("issueDate"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("issueDate"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("issueDate"), endDate));
            }

            // Kết hợp tất cả bằng AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
