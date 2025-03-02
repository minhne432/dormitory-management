package com.example.demo.specifications;

import com.example.demo.dto.RoomFilter;
import com.example.demo.entity.Room;
import com.example.demo.entity.Dormitory;
import com.example.demo.entity.RoomType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {
    public static Specification<Room> filter(RoomFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // JOIN tới dormitory để filter theo dormName
            Join<Object, Object> dormJoin = root.join("dormitory", JoinType.LEFT);
            // JOIN tới roomType nếu cần filter capacity, price, gender
            Join<Object, Object> rtJoin = root.join("roomType", JoinType.LEFT);

            // Lọc theo roomNumber (chứa 1 phần chuỗi)
            if (StringUtils.hasText(filter.getRoomNumber())) {
                predicates.add(cb.like(root.get("roomNumber"), "%" + filter.getRoomNumber() + "%"));
            }

            // Lọc theo dormName (chứa 1 phần chuỗi)
            if (StringUtils.hasText(filter.getDormName())) {
                predicates.add(cb.like(dormJoin.get("dormName"), "%" + filter.getDormName() + "%"));
            }

            // Lọc theo maxCapacity
            if (filter.getMaxCapacity() != null) {
                predicates.add(cb.equal(rtJoin.get("maxCapacity"), filter.getMaxCapacity()));
            }

            // Lọc theo gender (Nam / Nữ)
            if (StringUtils.hasText(filter.getGender())) {
                predicates.add(cb.equal(rtJoin.get("gender"), filter.getGender()));
            }

            // Lọc theo price
            if (filter.getPrice() != null) {
                predicates.add(cb.equal(rtJoin.get("price"), filter.getPrice()));
            }

            // Lọc theo status
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
