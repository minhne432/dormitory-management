package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HelpController {

    @GetMapping("/student/help")
    public String showHelpPage(Model model) {
        // danh sách các liên hệ hỗ trợ
        List<Map<String, String>> contacts = List.of(
                Map.of("role", "Quản lý KTX",            "name", "Ông Nguyễn Văn A",    "phone", "0901234567"),
                Map.of("role", "Tổ bảo vệ",             "name", "Anh Trần Văn B",      "phone", "0912345678"),
                Map.of("role", "Công an TP. Cần Thơ",    "name", "Trung tá Lê Thị C",    "phone", "0923456789")
        );
        model.addAttribute("contacts", contacts);
        return "student/help";
    }
}
