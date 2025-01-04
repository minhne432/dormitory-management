package com.example.demo.specifications;

import com.example.demo.entity.ApprovedApplication;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ApprovedApplicationSpecification {

    /**
     * Trả về một Specification dựa trên các thông số tìm kiếm (filter).
     */
    public static Specification<ApprovedApplication> filter(
            Long applicationId,
            LocalDate submissionDate,
            LocalDate approvalDate,
            String dormitoryArea,
            String address,
            String department
    ) {
        return (root, query, criteriaBuilder) -> {
            // Sử dụng CriteriaQuery xây dựng Predicate
            // Kết quả trả về sẽ là tiêu chí kết hợp (criteria) của các trường

            // Lưu ý: root.<Kiểu dữ liệu>("tên_thuộc_tính_trong_entity")
            //        criteriaBuilder.equal(...), criteriaBuilder.like(...) vv.

            // Chúng ta xây chuỗi Predicate 1 cách tuần tự
            var predicates = criteriaBuilder.conjunction();

            // applicationId
            if (applicationId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("applicationId"), applicationId));
            }

            // submissionDate
            if (submissionDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("submissionDate"), submissionDate));
            }

            // approvalDate
            if (approvalDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("approvalDate"), approvalDate));
            }

            // dormitoryArea
            if (dormitoryArea != null && !dormitoryArea.isEmpty()) {
                // Ví dụ: Tìm kiếm chính xác (equal) hoặc chứa chuỗi (like)
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(root.get("dormitoryArea"), "%" + dormitoryArea + "%"));
            }

            // address
            if (address != null && !address.isEmpty()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }

            // department
            if (department != null && !department.isEmpty()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(root.get("department"), "%" + department + "%"));
            }

            // Trả về Predicate cuối cùng
            return predicates;
        };
    }
}
