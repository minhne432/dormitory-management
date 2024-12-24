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
     * Liệt kê các đơn với phân trang và lọc theo khoảng thời gian (submissionDate).
     *  - URL ví dụ: GET /manager/applications?startDate=2024-01-01&endDate=2024-12-31&page=0&size=10
     */
    @GetMapping
    public String listApplications(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,

            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,

            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,

            Model model
    ) {
        // Nếu không chọn startDate/endDate thì gán mặc định
        if (startDate == null) {
            // Vd: lấy từ rất xa
            startDate = LocalDate.of(2000, 1, 1);
        }
        if (endDate == null) {
            // Lấy tới hôm nay hoặc xa hơn
            endDate = LocalDate.now().plusDays(1);
        }
            // Tạo Pageable object với sắp xếp giảm dần theo submissionDate
        Pageable pageable = PageRequest.of(page, size, Sort.by("submissionDate").descending());
            // Gọi repository để lấy data chỉ với status = pending
        Page<Application> applicationPage = applicationRepository
                .findBySubmissionDateBetweenAndStatus(
                        startDate,
                        endDate,
                        Application.ApplicationStatus.pending,
                        pageable);

        // Đưa data vào Model để hiển thị
        model.addAttribute("applications", applicationPage.getContent()); // danh sách
        model.addAttribute("currentPage", applicationPage.getNumber());  // trang hiện tại
        model.addAttribute("totalPages", applicationPage.getTotalPages());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "manager/application/list";
        // -> Bạn sẽ tạo file Thymeleaf: resources/templates/manager/application/list.html
    }

    /**
     * Xem chi tiết 1 đơn đăng ký
     * URL: GET /manager/applications/{id}
     */
    @GetMapping("/{id}")
    public String viewApplicationDetail(@PathVariable("id") Long applicationId, Model model) {
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            // Xử lý nếu không tìm thấy
            return "redirect:/manager/applications?error=notfound";
        }

        Application application = optionalApp.get();
        // Đưa application lên Model
        model.addAttribute("appDetail", application);
        // -> Trong Thymeleaf, bạn có thể truy cập application.student để xem thông tin sinh viên
        return "manager/application/detail";
        // -> File Thymeleaf: detail.html
    }

    /**
     * Phê duyệt (approve) đơn
     * POST /manager/applications/{id}/approve
     */
    @PostMapping("/{id}/approve")
    public String approveApplication(@PathVariable("id") Long applicationId) {
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

        return "redirect:/manager/applications?success=approved";
    }

    /**
     * Từ chối (reject) đơn
     * POST /manager/applications/{id}/reject
     */
    @PostMapping("/{id}/reject")
    public String rejectApplication(@PathVariable("id") Long applicationId) {
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            return "redirect:/manager/applications?error=notfound";
        }
        Application application = optionalApp.get();

        Manager currentManager = getCurrentManager();
        application.setStatus(ApplicationStatus.rejected);
        application.setApprovalDate(LocalDate.now());
        application.setApprovedBy(currentManager);

        applicationRepository.save(application);

        return "redirect:/manager/applications?success=rejected";
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
