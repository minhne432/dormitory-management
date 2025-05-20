package com.example.demo.controller;

import com.example.demo.entity.DashboardSummary;
import com.example.demo.entity.Room;
import com.example.demo.entity.Student;
import com.example.demo.repository.DashboardSummaryRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.NewsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.service.StudentRoomService; // đã có trong dự án

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;
    private final StudentRoomService studentRoomService; // đã có trong dự án

    @Autowired
    private DashboardSummaryRepository dashboardSummaryRepository;

    private final NewsService newsService;


    @Autowired
    public LoginController(StudentRepository studentRepository,
                           StudentRoomService studentRoomService,
                           NewsService newsService) {
        this.studentRepository = studentRepository;
        this.studentRoomService = studentRoomService;
        this.newsService = newsService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "registerSuccess", required = false) String registerSuccess,
                                Authentication authentication // 👈 Thêm vào đây để kiểm tra đăng nhập
    ) {
        // ✅ Nếu đã đăng nhập thì chuyển hướng theo role
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT"))) {
                return "redirect:/student/home";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"))) {
                return "redirect:/manager/home";
            }
        }

        // ✅ Nếu chưa đăng nhập thì hiển thị trang login như bình thường
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }
        if (registerSuccess != null) {
            model.addAttribute("message", "Register successfully, please login!");
        }

        return "login";
    }


    @GetMapping("/student/home")
    public String studentDashboard(HttpSession session, Principal principal, Model model) {
        // 1) Lấy username (hoặc email) từ principal
        String username = principal.getName();

        // 2) Tìm đối tượng Student qua repository (hoặc service)
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 3) Đưa student vào session
        session.setAttribute("student", student);

        // 4) Lấy thông tin phòng thực tế
        Optional<Room> roomOpt = studentRoomService.getCurrentRoomForLoggedStudent();
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            model.addAttribute("hasRoom", true);
            model.addAttribute("roomNumber", room.getRoomNumber());
            model.addAttribute("building", room.getDormitory().getDormName());
        } else {
            model.addAttribute("hasRoom", false);
        }

        // Lấy 5 bản tin mới nhất
        model.addAttribute("newsList", newsService.getLatest(3));

        // 6) Trả về view
        return "student/home";
    }


    @GetMapping("/manager/home")
    public String home(Model model) {
        // 1. Lấy dữ liệu từ view (chỉ có 1 dòng duy nhất)
        DashboardSummary stats = dashboardSummaryRepository.findAll().stream().findFirst().orElse(new DashboardSummary());

        model.addAttribute("totalStudents", stats.getStudentCount());
        model.addAttribute("vacantRooms", stats.getAvailableRooms());
        model.addAttribute("pendingRequests", stats.getPendingApplications());
        model.addAttribute("newRequestsToday", stats.getNewServiceRequests());

        // 2. Lấy tin tức
        model.addAttribute("newsList", newsService.getLatest(3));

        return "manager/home";
    }

    // Class đại diện cho một mục tin bảng tin (News Item) - Inner Class
    public static class NewsItem {
        private String title;
        private String content;

        public NewsItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
