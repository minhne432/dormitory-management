// filepath: src/main/java/com/example/demo/controller/BillController.java
package com.example.demo.controller;

import com.example.demo.dto.BillFilterRequestForManager;
import com.example.demo.entity.Bill;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.BillService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bills/manager")
public class BillController {

    private final BillService billService;
    private final BillRepository billRepository;

    @Autowired
    public BillController(BillService billService,
                          BillRepository billRepository) {
        this.billService = billService;
        this.billRepository = billRepository;
    }

    /* -------------------- FILTER & PAGINATION -------------------- */

    // 1. Hiển thị trang mặc định (page = 0)
    @GetMapping("/filter")
    public String showFilterForm(@ModelAttribute("billFilterRequest") BillFilterRequestForManager filter,
                                 Model model) {
        return doFilter(filter, model);
    }

    // 2. Submit filter (POST) hoặc phân trang (GET) đều đi vào hàm này
    @PostMapping("/filter")
    public String filterBills(@ModelAttribute("billFilterRequest") BillFilterRequestForManager filter,
                              Model model) {
        return doFilter(filter, model);
    }

    // 3. Hàm dùng chung để filter và phân trang
    private String doFilter(BillFilterRequestForManager filter, Model model) {
        Page<Bill> bills = billService.getBillsByFilterForanager(filter);
        model.addAttribute("bills", bills);
        model.addAttribute("billFilterRequest", filter); // giữ lại filter cũ trên form
        return "manager/bills/main";
    }

    /* -------------------- UPDATE STATUS -------------------- */

    @PostMapping("/updateStatus")
    @ResponseBody
    public ResponseEntity<String> updateBillStatus(@RequestParam("billId") Long billId,
                                                   @RequestParam("status") String status) {
        try {
            Bill.BillStatus billStatus = Bill.BillStatus.valueOf(status);
            billService.updateBillStatus(billId, billStatus);
            return ResponseEntity.ok("Cập nhật trạng thái thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trạng thái không hợp lệ.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật trạng thái.");
        }
    }

    /* -------------------- CHI TIẾT HÓA ĐƠN -------------------- */

    @GetMapping("/detail/{billId}")
    public String viewBillDetail(@PathVariable("billId") Long billId, Model model) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        model.addAttribute("bill", bill);
        return "manager/bills/detail";
    }
}
