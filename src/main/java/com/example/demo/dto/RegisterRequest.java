package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    // Có thể thêm trường khác như fullName, email... tùy nhu cầu
}
