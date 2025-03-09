package com.example.demo.specifications;

import com.example.demo.dto.RegistrationFilterDTO;
import com.example.demo.entity.StudentServiceRegistration;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceRegistrationSpecification {

    public static Specification<StudentServiceRegistration> filter(RegistrationFilterDTO filter, Long studentId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo sinh viên (studentId)
            predicates.add(cb.equal(root.get("student").get("studentId"), studentId));

            // Lọc theo dịch vụ ký túc xá (dormitoryService) nếu có
            if (filter.getDormitoryServiceId() != null) {
                predicates.add(cb.equal(root.get("dormitoryService").get("serviceId"), filter.getDormitoryServiceId()));
            }

            // Lọc theo trạng thái đăng ký nếu có
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
