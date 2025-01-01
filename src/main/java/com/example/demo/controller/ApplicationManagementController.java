package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.entity.Application.ApplicationStatus;
import com.example.demo.entity.Manager;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.ManagerRepository;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/manager/applications")
public class ApplicationManagementController {

    private final ApplicationRepository applicationRepository;
    private final ManagerRepository managerRepository;

    public ApplicationManagementController(ApplicationRepository applicationRepository,
                                           ManagerRepository managerRepository) {
        this.applicationRepository = applicationRepository;
        this.managerRepository = managerRepository;
    }

    /**
     * Xem chi tiết 1 đơn đăng ký
     * URL: GET /manager/applications/{id}
     */
    @GetMapping("/{id}")
    public String viewApplicationDetail(
            @PathVariable("id") Long applicationId,
            @RequestParam(required = false) String dormitoryArea,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            // Xử lý nếu không tìm thấy
            return "redirect:/manager/applications?error=notfound";
        }

        Application application = optionalApp.get();
        // Đưa application và các tham số lọc vào Model
        model.addAttribute("appDetail", application);
        model.addAttribute("dormitoryArea", dormitoryArea);
        model.addAttribute("address", address);
        model.addAttribute("department", department);
        model.addAttribute("page", page);

        return "manager/application/detail"; // File Thymeleaf: detail.html
    }


    /**
     * Phê duyệt (approve) đơn
     * POST /manager/applications/{id}/approve
     */
    @PostMapping("/{id}/approve")
    public String approveApplication(
            @PathVariable("id") Long applicationId,
            @RequestParam(required = false) String dormitoryArea,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page
    ) {
        // Lấy application
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            return "redirect:/manager/applications?error=notfound";
        }
        Application application = optionalApp.get();

        // Lấy Manager hiện tại (người đang đăng nhập)
        Manager currentManager = getCurrentManager();
        // Cập nhật trạng thái
        application.setStatus(ApplicationStatus.approved);
        application.setApprovalDate(LocalDate.now());
        application.setApprovedBy(currentManager);

        applicationRepository.save(application);

        // Truyền tham số lọc trở lại khi chuyển hướng
        return "redirect:/manager/applications/pending-applications?success=approved"
                + (dormitoryArea != null ? "&dormitoryArea=" + dormitoryArea : "")
                + (address != null ? "&address=" + address : "")
                + (department != null ? "&department=" + department : "")
                + "&page=" + page;
    }

    /**
     * Từ chối (reject) đơn
     * POST /manager/applications/{id}/reject
     */
    @PostMapping("/{id}/reject")
    public String rejectApplication(
            @PathVariable("id") Long applicationId,
            @RequestParam(required = false) String dormitoryArea,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page
    ) {
        // Logic từ chối
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            return "redirect:/manager/applications/pending-applications?error=notfound";
        }
        Application application = optionalApp.get();
        Manager currentManager = getCurrentManager();
        application.setStatus(ApplicationStatus.rejected);
        application.setApprovalDate(LocalDate.now());
        application.setApprovedBy(currentManager);
        applicationRepository.save(application);

        // Truyền lại tham số lọc khi chuyển hướng
        return "redirect:/manager/applications/pending-applications?success=rejected"
                + (dormitoryArea != null ? "&dormitoryArea=" + dormitoryArea : "")
                + (address != null ? "&address=" + address : "")
                + (department != null ? "&department=" + department : "")
                + "&page=" + page;
    }


    /**
     * Lấy Manager hiện tại từ SecurityContext
     * (tương tự cách bạn lấy Student hiện tại)
     */
    private Manager getCurrentManager() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("User is not authenticated");
        }
        String username = auth.getName();
        // Ví dụ: ManagerRepository có thể có findByUsername(...)
        Optional<Manager> optionalManager = managerRepository.findByUsername(username);
        return optionalManager.orElseThrow(() ->
                new IllegalStateException("No manager found for user " + username));
    }
}
