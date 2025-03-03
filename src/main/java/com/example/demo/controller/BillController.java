//filepath: src\main\java\com\example\demo\controller\BillController.java
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



    @GetMapping("/filter")
    public String showFilterForm(Model model) {
        return filterBills(new BillFilterRequestForManager(), model);
    }

    @PostMapping("/filter")
    public String filterBills(@ModelAttribute BillFilterRequestForManager filterRequest, Model model) {
        Page<Bill> bills = billService.getBillsByFilterForanager(filterRequest);
        model.addAttribute("bills", bills);
        model.addAttribute("billFilterRequest", filterRequest);
        return "manager/bills/main"; // Trả về view đầy đủ
    }


    @PostMapping("/updateStatus")
    @ResponseBody
    public ResponseEntity<String> updateBillStatus(@RequestParam("billId") Long billId,
                                                   @RequestParam("status") String status) {
        try {
            // Chuyển đổi chuỗi status thành enum nếu cần
            Bill.BillStatus billStatus = Bill.BillStatus.valueOf(status);
            // Gọi service cập nhật trạng thái (bạn có thể cài đặt thêm phương thức updateBillStatus trong BillService)
            billService.updateBillStatus(billId, billStatus);
            return ResponseEntity.ok("Cập nhật trạng thái thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trạng thái không hợp lệ.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật trạng thái.");
        }
    }

    // Hiển thị chi tiết hóa đơn
    @GetMapping("/detail/{billId}")
    public String viewBillDetail(@PathVariable("billId") Long billId, Model model) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        model.addAttribute("bill", bill);
        return "manager/bills/detail";
    }

}
