package com.example.demo.dto;

import com.example.demo.entity.StudentServiceRegistration.RegistrationStatus;
import lombok.Data;

@Data
public class RegistrationFilterDTO {
    private Long dormitoryServiceId; // Lọc theo dịch vụ ký túc xá (serviceId của DormitoryService)
    private RegistrationStatus status; // Lọc theo trạng thái đăng ký (pending, approved, rejected)
}
