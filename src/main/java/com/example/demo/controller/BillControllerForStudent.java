package com.example.demo.controller;

import com.example.demo.dto.BillFilterRequest;
import com.example.demo.entity.Bill;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.BillService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/bills")
public class BillControllerForStudent {

    @Autowired
    private BillService billService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/filter")
    public String filterBills(
            @RequestParam(required = false) Bill.BillStatus status,
            @RequestParam(required = false) Bill.BillType billType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        // 1. Lấy ID và roomId của sinh viên hiện tại
        Long studentId = studentService.getCurrentStudentId();
        Long roomId    = studentService.getCurrentRoomId(); // có thể null nếu chưa xếp phòng

        // 2. Đổ param vào DTO
        BillFilterRequest filter = new BillFilterRequest();
        filter.setStudentId(studentId);
        filter.setRoomId(roomId);
        filter.setStatus(status);
        filter.setBillType(billType);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        filter.setPage(page);
        filter.setSize(size);

        // 3. Xử lý lấy ngày join room một cách null-safe
        var currentAssignment = studentService.getCurrentRoomAssignment();
        if (currentAssignment != null && currentAssignment.getAssignedDate() != null) {
            filter.setRoomJoinDate(currentAssignment.getAssignedDate());
        }
        // nếu currentAssignment == null, filter.roomJoinDate để mặc định null (không lọc theo ngày join)

        // 4. Lấy dữ liệu hóa đơn đã lọc
        Page<Bill> bills = billService.getBillsByFilter(filter);

        // 5. Truyền vào view
        model.addAttribute("bills",  bills);
        model.addAttribute("filter", filter);

        return "student/bills/filter";
    }



    @PostMapping("/detail")
    public String viewBillDetail(@RequestParam("billId") Long billId, Model model) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        model.addAttribute("bill", bill);
        return "student/bills/detail";
    }

}
