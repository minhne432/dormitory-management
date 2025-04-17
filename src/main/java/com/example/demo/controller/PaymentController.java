package com.example.demo.controller;

import com.example.demo.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
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


}
