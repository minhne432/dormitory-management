package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // Thêm annotation để dùng Builder Pattern
public class DormitoryServiceDTO {
    private Long serviceId;

    @NotBlank(message = "Tên dịch vụ không được để trống")
    private String serviceName;

    @NotNull(message = "Giá dịch vụ không được để trống")
    private Double unitPrice;

    private String unit;
    private String description;
}

