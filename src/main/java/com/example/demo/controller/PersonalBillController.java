package com.example.demo.controller;

import com.example.demo.entity.Bill;
import com.example.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personal-bills")
@RequiredArgsConstructor
public class PersonalBillController {

    private final BillService billService;

    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestParam List<Long> registrationIds) {
        try {
            Bill bill = billService.createBillForRegistrations(registrationIds);
            // Trả về JSON thông báo thành công
            return ResponseEntity.ok(Collections.singletonMap("message", "Hóa đơn đã được tạo thành công!"));
        } catch (ResponseStatusException ex) {
            // Trả về JSON thông báo lỗi với mã lỗi tương ứng
            return ResponseEntity.status(ex.getStatusCode())
                    .body(Collections.singletonMap("error", ex.getReason()));
        }
    }
}
