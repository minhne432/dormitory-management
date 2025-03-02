package com.example.demo.dto;

import com.example.demo.entity.Bill;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BillFilterRequestForManager {
    private Long billId; // Thêm thuộc tính billId để lọc theo ID của hóa đơn
    private Long studentId;
    private Bill.BillStatus status;
    private Bill.BillType billType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int page = 0;
    private int size = 10;
}
