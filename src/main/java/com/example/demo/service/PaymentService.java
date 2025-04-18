package com.example.demo.service;

import com.example.demo.config.VNPayConfig;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Room;
import com.example.demo.entity.Student;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RoomAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository payRepo;
    @Autowired VNPayConfig vnpCfg;
    @Autowired BillRepository billRepo;

    @Autowired
    RoomAssignmentRepository roomAssignmentRepository;

    public String createVNPayCheckout(Long billId, HttpServletRequest req) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        // 1. Tạo Payment PENDING
        Payment payment = Payment.builder()
                .bill(bill)
                .amountPaid(bill.getTotalAmount())
                .status(Payment.PaymentStatus.pending)
                .paymentDate(LocalDate.now())
                .paymentMethod("VNPAY")
                .vnpTxnRef(vnpCfg.getRandomNumber(8))
                .build();
        payRepo.save(payment);

        // 2. Sinh URL VNPay (giống CartController.createPayment)
        return buildVNPayUrl(payment, req);
    }

    public void handleReturn(Map<String,String> params) {
        try {
            if (!vnpCfg.verifySignature(params))
                throw new RuntimeException("Invalid hash");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding lỗi trong khi xác thực chữ ký", e);
        }
        String ref = params.get("vnp_TxnRef");
        Payment payment = payRepo.findByVnpTxnRef(ref)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if("00".equals(params.get("vnp_ResponseCode"))) {
            payment.setStatus(Payment.PaymentStatus.completed);
            payment.getBill().setStatus(Bill.BillStatus.paid);
            //set paid date
            payment.getBill().setPaidDate(LocalDateTime.now());
        } else {
            payment.setStatus(Payment.PaymentStatus.pending); // hoặc failed tuỳ bạn
        }
        payRepo.save(payment); // cascade sẽ lưu Bill
    }

    public String buildVNPayUrl(Payment payment, HttpServletRequest request) {
        try {
            long amountInVND = (long) (payment.getAmountPaid() * 100);

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_TmnCode", vnpCfg.getVnp_TmnCode());
            vnp_Params.put("vnp_Amount", String.valueOf(amountInVND));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", payment.getVnpTxnRef());
            vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don: " + payment.getVnpTxnRef());
            vnp_Params.put("vnp_OrderType", "bill");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", vnpCfg.getVnp_ReturnUrl());
            vnp_Params.put("vnp_IpAddr", vnpCfg.getIpAddress(request));

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
            cld.add(Calendar.MINUTE, 15);
            vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, "UTF-8"));
                query.append(URLEncoder.encode(fieldName, "UTF-8"))
                        .append('=').append(URLEncoder.encode(fieldValue, "UTF-8"));
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }

            String vnp_SecureHash = vnpCfg.hmacSHA512(vnpCfg.getVnp_HashSecret(), hashData.toString());
            query.append("&vnp_SecureHash=").append(vnp_SecureHash);

            return vnpCfg.getVnp_PayUrl() + "?" + query.toString();

        } catch (Exception e) {
            throw new RuntimeException("Không tạo được URL thanh toán VNPAY", e);
        }
    }

    public List<Payment> getPaymentsByStudentAndRoom(Student student) {
        // Lấy phòng hiện tại của sinh viên
        Room currentRoom = roomAssignmentRepository
                .findByStudentStudentIdAndEndDateIsNull(student.getStudentId())
                .getRoom();

        return payRepo.findByStudentOrRoom(student, currentRoom);
    }




}

