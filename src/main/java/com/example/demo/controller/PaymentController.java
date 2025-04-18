package com.example.demo.controller;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Student;
import com.example.demo.service.PaymentService;
import com.example.demo.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
public class PaymentController {
    private final PaymentService paymentService;

    private final StudentService studentService;

    public PaymentController(PaymentService paymentService, StudentService studentService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
    }
    @PostMapping("/bills/{id}/checkout")
    public RedirectView checkout(@PathVariable Long id, HttpServletRequest req){
        String url = paymentService.createVNPayCheckout(id, req);
        return new RedirectView(url);
    }

    @GetMapping("/payment/vnpay_return")
    public String vnpReturn(@RequestParam Map<String,String> all, Model model){
        paymentService.handleReturn(all);
        Map<String, String> params = all;
        model.addAttribute("vnp_TxnRef", params.get("vnp_TxnRef"));
        model.addAttribute("vnp_Amount", Long.parseLong(params.get("vnp_Amount")) / 100); // chuyển về VND
        model.addAttribute("vnp_OrderInfo", params.get("vnp_OrderInfo"));
        model.addAttribute("vnp_BankCode", params.get("vnp_BankCode"));
        model.addAttribute("vnp_PayDate", params.get("vnp_PayDate"));
        model.addAttribute("vnp_TransactionNo", params.get("vnp_TransactionNo"));
        model.addAttribute("vnp_ResponseCode", params.get("vnp_ResponseCode"));


        model.addAttribute("message","Thanh toán thành công!");
        return "student/bills/payment_result";
    }


    @GetMapping("/student/payments")
    public String viewPaymentHistory(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.getStudentByUsername(username);

        List<Payment> payments = paymentService.getPaymentsByStudentAndRoom(student);

        model.addAttribute("payments", payments);
        return "student/payment/history";
    }


}
