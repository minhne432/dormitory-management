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

    // Ví dụ thêm bộ lọc động cho các thuộc tính khác, ví dụ theo roomId
    public static Specification<ServiceUsage> hasRoomId(Long roomId) {
        return (root, query, criteriaBuilder) -> {
            if (roomId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("room").get("roomId"), roomId);
        };
    }

    // Tương tự cho currentReading
    public static Specification<ServiceUsage> hasCurrentReading(Double currentReading) {
        return (root, query, criteriaBuilder) -> {
            if (currentReading == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("currentReading"), currentReading);
        };
    }

    // Có thể thêm các bộ lọc cho previousReading, recordDate, invoiced,...
    public static Specification<ServiceUsage> hasPreviousReading(Double previousReading) {
        return (root, query, criteriaBuilder) -> {
            if (previousReading == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("previousReading"), previousReading);
        };
    }

    // Loại bỏ hoặc comment out hasRecordDate cũ
    // public static Specification<ServiceUsage> hasRecordDate(String recordDateStr) { ... }

    // Thêm Specification mới cho lọc theo khoảng thời gian recordDate
    public static Specification<ServiceUsage> hasRecordDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction(); // Không lọc theo recordDate nếu cả hai đều null
            }
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("recordDate"), startDate, endDate); // Lọc trong khoảng
            }
            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("recordDate"), startDate); // Lọc từ startDate trở đi
            }
            // if (endDate != null) - chỉ còn trường hợp endDate != null
            return criteriaBuilder.lessThanOrEqualTo(root.get("recordDate"), endDate); // Lọc đến endDate
        };
    }


    public static Specification<ServiceUsage> hasInvoiced(String invoiced) {
        return (root, query, criteriaBuilder) -> {
            if (invoiced == null || invoiced.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("invoiced"), ServiceUsage.InvoicedStatus.valueOf(invoiced));
        };
    }
}