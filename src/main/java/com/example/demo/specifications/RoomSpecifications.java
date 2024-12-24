package com.example.demo.specifications;

import com.example.demo.entity.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecifications {

    /**
     * Specification để lọc theo tên khu ký túc xá.
     * Nếu dormitoryName là null hoặc rỗng, không áp dụng điều kiện lọc.
     */
    public static Specification<Room> hasDormitoryName(String dormitoryName) {
        return (root, query, criteriaBuilder) -> {
            if (dormitoryName == null || dormitoryName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("dormitory").get("dormName")),
                    dormitoryName.toLowerCase()
            );
        };
    }

    /**
     * Specification để lọc theo số người tối thiểu.
     * Nếu minCapacity là null, không áp dụng điều kiện lọc.
     */
    public static Specification<Room> hasMinCapacity(Integer minCapacity) {
        return (root, query, criteriaBuilder) -> {
            if (minCapacity == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("maxCapacity"), minCapacity);
        };
    }

    /**
     * Specification để lọc theo số người tối đa.
     * Nếu maxCapacity là null, không áp dụng điều kiện lọc.
     */
    public static Specification<Room> hasMaxCapacity(Integer maxCapacity) {
        return (root, query, criteriaBuilder) -> {
            if (maxCapacity == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("maxCapacity"), maxCapacity);
        };
    }

    /**
     * Specification để lọc các phòng có trạng thái AVAILABLE.
     */
    public static Specification<Room> hasAvailableStatus() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), Room.RoomStatus.available);
    }

    /**
     * Specification để lọc các phòng chưa đầy.
     */
    public static Specification<Room> hasAvailableOccupancy() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("currentOccupancy"), root.get("maxCapacity"));
    }
}
