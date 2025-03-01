package com.example.demo.dto;

import com.example.demo.entity.Bill.BillStatus;
import com.example.demo.entity.Bill.BillType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BillFilterRequest {
    private Long studentId;             // Lọc theo studentId
    private BillStatus status;          // Lọc theo status: overdue, paid, unpaid
    private BillType billType;          // Lọc theo type: PHONG, DIEN_NUOC, DICH_VU

    // Khoảng thời gian cho issueDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // Các tham số phân trang
    private int page = 0;     // Số trang (default 0)
    private int size = 10;    // Kích thước trang (default 10)
}
