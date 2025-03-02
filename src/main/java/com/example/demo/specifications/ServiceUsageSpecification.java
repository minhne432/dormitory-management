package com.example.demo.specifications;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.DormitoryService;
import com.example.demo.entity.DormitoryService.ServiceType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

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

    public static Specification<ServiceUsage> hasRecordDate(String recordDateStr) {
        return (root, query, criteriaBuilder) -> {
            if (recordDateStr == null || recordDateStr.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // Giả sử định dạng yyyy-MM-dd
            return criteriaBuilder.equal(root.get("recordDate"), java.time.LocalDate.parse(recordDateStr));
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
