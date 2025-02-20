package com.example.demo.specifications;

import com.example.demo.entity.RoomServiceUsage;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RoomServiceUsageSpecification {

    public static Specification<RoomServiceUsage> hasRoomId(Long roomId) {
        return (root, query, cb) -> {
            if (roomId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("id").get("roomId"), roomId);
        };
    }

    public static Specification<RoomServiceUsage> hasRoomNumber(String roomNumber) {
        return (root, query, cb) -> {
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("roomNumber")), "%" + roomNumber.toLowerCase() + "%");
        };
    }

    public static Specification<RoomServiceUsage> hasRecordDate(LocalDate recordDate) {
        return (root, query, criteriaBuilder) -> {
            if (recordDate == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id").get("recordDate"), recordDate);
        };
    }


    public static Specification<RoomServiceUsage> hasServiceName(String serviceName) {
        return (root, query, cb) -> {
            if (serviceName == null || serviceName.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("serviceName")), "%" + serviceName.toLowerCase() + "%");
        };
    }
}
