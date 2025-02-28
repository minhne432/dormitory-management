package com.example.demo.controller;

import com.example.demo.entity.Bill;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    // Hiển thị form tạo hóa đơn
    @GetMapping("/create")
    public String showCreateBillForm() {
        return "manager/bill_form";
    }

    // Xử lý tạo hóa đơn khi submit form
    @PostMapping("/create")
    public String createBill(@RequestParam("studentId") Long studentId, Model model) {
        try {
            Bill bill = billService.createRoomBill(studentId);
            model.addAttribute("bill", bill);
            return "manager/bill_detail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/bill_form";
        }
    }
}
