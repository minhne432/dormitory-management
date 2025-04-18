package com.example.demo.specifications;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.DormitoryService;
import com.example.demo.entity.DormitoryService.ServiceType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;

public class ServiceUsageSpecification {

    // Điều kiện mặc định: chỉ lấy ServiceUsage có serviceType ROOM
    public static Specification<ServiceUsage> hasRoomServiceType() {
        return (root, query, criteriaBuilder) -> {
            Join<ServiceUsage, DormitoryService> serviceJoin = root.join("service", JoinType.INNER);
            return criteriaBuilder.equal(serviceJoin.get("serviceType"), ServiceType.ROOM);
        };
    }

    // Bộ lọc theo roomId
    public static Specification<ServiceUsage> hasRoomId(Long roomId) {
        return (root, query, criteriaBuilder) -> {
            if (roomId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("room").get("roomId"), roomId);
        };
    }

    // ✅ Bộ lọc theo roomNumber (contains, không phân biệt HOA/thường)
    public static Specification<ServiceUsage> hasRoomNumber(String roomNumber) {
        return (root, query, cb) -> {
            if (roomNumber == null || roomNumber.isBlank()) {
                return cb.conjunction();
            }
            Expression<String> numberExp = cb.lower(root.get("room").get("roomNumber"));
            return cb.like(numberExp, "%" + roomNumber.toLowerCase().trim() + "%");
        };
    }

    // Bộ lọc theo currentReading
    public static Specification<ServiceUsage> hasCurrentReading(Double currentReading) {
        return (root, query, criteriaBuilder) -> {
            if (currentReading == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("currentReading"), currentReading);
        };
    }

    // Bộ lọc theo previousReading
    public static Specification<ServiceUsage> hasPreviousReading(Double previousReading) {
        return (root, query, criteriaBuilder) -> {
            if (previousReading == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("previousReading"), previousReading);
        };
    }

    // Bộ lọc theo khoảng thời gian recordDate
    public static Specification<ServiceUsage> hasRecordDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("recordDate"), startDate, endDate);
            }
            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("recordDate"), startDate);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("recordDate"), endDate);
        };
    }

    // Bộ lọc theo invoiced
    public static Specification<ServiceUsage> hasInvoiced(String invoiced) {
        return (root, query, criteriaBuilder) -> {
            if (invoiced == null || invoiced.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("invoiced"), ServiceUsage.InvoicedStatus.valueOf(invoiced));
        };
    }
}
