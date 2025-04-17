package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.NewsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
                                @RequestParam(value = "registerSuccess", required = false) String registerSuccess
    ) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }
        if (registerSuccess != null) {
            model.addAttribute("message", "Register successfully, please login!");
        }
        return "login"; // login.html
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
        model.addAttribute("newsList", newsService.getLatest(5));

        // 6) Trả về view
        return "student/home";
    }


    @GetMapping("/manager/home")
    public String home(Model model) {
        // 1. Giả lập dữ liệu cho các thông tin tổng quan
        int totalStudents = 1350;
        int vacantRooms = 68;
        int pendingRequests = 12;
        int newRequestsToday = 3;
        int newRequestsWeek = 20;

        // 2. Giả lập dữ liệu cho bảng tin ký túc xá
        List<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem("Lịch kiểm tra PCCC đột xuất", "Ban quản lý KTX thông báo lịch kiểm tra PCCC đột xuất vào sáng ngày 8/12 tại tất cả các khu."));
        newsList.add(new NewsItem("Thông báo điều chỉnh giờ giới nghiêm", "Từ ngày 15/12, giờ giới nghiêm tại KTX sẽ được điều chỉnh thành 23h00."));
        newsList.add(new NewsItem("Tuyển tình nguyện viên cho chương trình Noel ấm áp", "CLB Tình nguyện KTX tuyển волонтер hỗ trợ chương trình Noel ấm áp, hạn đăng ký 10/12."));

        // 3. Truyền dữ liệu sang template thông qua Model
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("vacantRooms", vacantRooms);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("newRequestsToday", newRequestsToday);
        model.addAttribute("newRequestsWeek", newRequestsWeek);
        model.addAttribute("newsList", newsList); // Truyền danh sách bảng tin

        return "manager/home"; // Trả về tên template 'home.html' (đặt trong thư mục 'manager')
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
