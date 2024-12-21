package com.example.demo.dto;

import lombok.Data;

@Data
public class DormApplicationForm {
    private String fullName;       // Họ tên (readonly)
    private String department;     // Khoa (readonly)
    private String studentClass;   // Lớp (readonly)
    private String phoneNumber;    // SĐT (có thể cập nhật)
    private String email;          // Email (có thể cập nhật)
    private String address;        // Địa chỉ (readonly)
    private String dormitoryArea;  // Khu KTX mong muốn
    private String note;
}
