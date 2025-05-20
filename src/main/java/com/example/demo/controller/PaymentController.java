package com.example.demo.controller;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Student;
import com.example.demo.service.EmailService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.StudentService;
import jakarta.mail.MessagingException;
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

    private final EmailService emailService;
    public PaymentController(PaymentService paymentService, StudentService studentService, EmailService emailService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
        this.emailService = emailService;
    }
    @PostMapping("/bills/{id}/checkout")
    public RedirectView checkout(@PathVariable Long id, HttpServletRequest req){
        String url = paymentService.createVNPayCheckout(id, req);
        return new RedirectView(url);
    }

    @GetMapping("/payment/vnpay_return")
    public String vnpReturn(@RequestParam Map<String,String> all, Model model) {
        paymentService.handleReturn(all);

        // parse parameters
        String txnRef       = all.get("vnp_TxnRef");
        String orderInfo    = all.get("vnp_OrderInfo");
        long   amount       = Long.parseLong(all.get("vnp_Amount")) / 100;
        String bankCode     = all.get("vnp_BankCode");
        String payDate      = all.get("vnp_PayDate");
        String transactionNo= all.get("vnp_TransactionNo");
        String responseCode = all.get("vnp_ResponseCode");
        String statusText   = "00".equals(responseCode) ? "Thành công" : "Lỗi: " + responseCode;

        // send email
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.getStudentByUsername(username);
        String email = student.getEmail();
        if (email != null && !email.isEmpty()) {
            String subject = "Thông báo thanh toán thành công - Hóa đơn " + txnRef;
            String htmlBody = buildPaymentHtml(txnRef, orderInfo, amount, bankCode, payDate, transactionNo, statusText);
            try {
                emailService.sendHtmlEmail(email, subject, htmlBody);
            } catch (MessagingException e) {
                // you can log this
                e.printStackTrace();
            }
        }

        // add to model for browser view
        model.addAttribute("vnp_TxnRef", txnRef);
        model.addAttribute("vnp_Amount", amount);
        model.addAttribute("vnp_OrderInfo", orderInfo);
        model.addAttribute("vnp_BankCode", bankCode);
        model.addAttribute("vnp_PayDate", payDate);
        model.addAttribute("vnp_TransactionNo", transactionNo);
        model.addAttribute("vnp_ResponseCode", responseCode);
        model.addAttribute("message","Thanh toán thành công!");
        return "student/bills/payment_result";
    }

    private String buildPaymentHtml(
            String txnRef,
            String orderInfo,
            long amount,
            String bankCode,
            String payDate,
            String transactionNo,
            String statusText
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head>")
                .append("<meta charset='UTF-8'>")
                .append("<style>")
                .append("  table { border-collapse: collapse; width: 100%; }")
                .append("  td, th { border: 1px solid #ddd; padding: 8px; }")
                .append("  th { background-color: #f4f4f4; }")
                .append("</style>")
                .append("</head><body>")
                .append("<h2 style='color: #2c7'>Kết quả thanh toán VNPAY</h2>")
                .append("<p>Thanh toán thành công!</p>")
                .append("<table>")
                .append(tr("Mã giao dịch (VNPAY)", transactionNo))
                .append(tr("Mã đơn hàng", txnRef))
                .append(tr("Thông tin đơn hàng", orderInfo))
                .append(tr("Số tiền thanh toán", amount + " ₫"))
                .append(tr("Ngân hàng", bankCode))
                .append(tr("Thời gian thanh toán", payDate))
                .append(tr("Trạng thái giao dịch", statusText))
                .append("</table>")
                .append("</body></html>");
        return sb.toString();
    }

    private String tr(String label, String value) {
        return "<tr><th style='text-align:left;'>" + label + "</th><td>" + value + "</td></tr>";
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
