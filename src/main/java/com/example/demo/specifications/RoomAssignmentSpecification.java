package com.example.demo.specifications;

import com.example.demo.entity.RoomAssignment;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate; // ✅ ĐÚNG

// RoomAssignmentSpecification.java
public class RoomAssignmentSpecification {

    public static Specification<RoomAssignment> filter(Long studentId, Long roomId) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();

            // chỉ những sinh viên đang ở (chưa có endDate)
            preds.add(cb.isNull(root.get("endDate")));

            if (studentId != null)
                preds.add(cb.equal(root.join("student").get("studentId"), studentId));

            if (roomId != null)
                preds.add(cb.equal(root.join("room").get("roomId"), roomId));

            return cb.and(preds.toArray(new Predicate[0]));
        };
    }
}
