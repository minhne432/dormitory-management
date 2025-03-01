package com.example.demo.controller;

import com.example.demo.dto.BillFilterRequest;
import com.example.demo.entity.Bill;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/bills")
public class BillControllerForStudent {

    @Autowired
    private BillService billService;

    @GetMapping("/filter")
    public String filterBills(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Bill.BillStatus status,
            @RequestParam(required = false) Bill.BillType billType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        // Map param vào DTO
        BillFilterRequest filter = new BillFilterRequest();
        filter.setStudentId(studentId);
        filter.setStatus(status);
        filter.setBillType(billType);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        filter.setPage(page);
        filter.setSize(size);

        // Lấy danh sách hóa đơn với phân trang và lọc
        Page<Bill> bills = billService.getBillsByFilter(filter);

        // Truyền dữ liệu vào view
        model.addAttribute("bills", bills);
        model.addAttribute("filter", filter);

        return "student/bills/filter"; // Trả về view Thymeleaf
    }
}
