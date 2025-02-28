package com.example.demo.repository;

import com.example.demo.entity.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {
    // Bạn có thể thêm các phương thức truy vấn tuỳ ý ở đây nếu cần
}
