package com.example.demo.controller;

import com.example.demo.dto.ApprovedStudentServiceRequestFilter;
import com.example.demo.entity.ApprovedStudentServiceRequest;
import com.example.demo.service.ApprovedStudentServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/approved-requests")
public class ApprovedStudentServiceRequestController {

    @Autowired
    private ApprovedStudentServiceRequestService service;

    @GetMapping
    public String listApprovedRequests(
            @ModelAttribute("filter") ApprovedStudentServiceRequestFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            @RequestHeader(value="X-Requested-With", required=false) String requestedWith
    ) {
        Page<ApprovedStudentServiceRequest> approvedRequests =
                service.getApprovedRequests(filter, PageRequest.of(page, size));
        model.addAttribute("approvedRequests", approvedRequests);

        // Nếu là AJAX request, trả về fragment thay vì toàn bộ trang
        if ("XMLHttpRequest".equals(requestedWith)) {
            return "layout/manager/fragment/approved-requests :: approvedRequestsList";
        }


        // Nếu không phải AJAX, trả về toàn bộ template gốc:
        return "manager/approved-requests";  // Tên template chính
    }
}

