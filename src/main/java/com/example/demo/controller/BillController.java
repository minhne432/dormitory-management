//filepath: src\main\java\com\example\demo\controller\BillController.java
package com.example.demo.controller;

import com.example.demo.dto.BillFilterRequestForManager;
import com.example.demo.entity.Bill;
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


    @GetMapping("/filter")
    public String showFilterForm(Model model) {
        // Giả sử nếu filter rỗng sẽ trả về tất cả hóa đơn
        model.addAttribute("billFilterRequest", new BillFilterRequestForManager());
        Page<Bill> bills = billService.getBillsByFilterForanager(new BillFilterRequestForManager());
        model.addAttribute("bills", bills);
        return "manager/bills/main";  // View chứa form và danh sách hóa đơn
    }

    // Xử lý lọc hóa đơn và hiển thị kết quả
    @PostMapping("/filter")
    public String filterBills(@ModelAttribute BillFilterRequestForManager filterRequest, Model model, HttpServletRequest request) {
        Page<Bill> bills = billService.getBillsByFilterForanager(filterRequest);
        model.addAttribute("bills", bills);
        // Nếu là AJAX request, trả về fragment
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "manager/bills/list :: billListFragment";
        }
        // Nếu không, trả về view đầy đủ
        return "manager/bills/list";
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


}
