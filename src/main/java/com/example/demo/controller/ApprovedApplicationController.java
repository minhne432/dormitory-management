package com.example.demo.controller;

import com.example.demo.entity.ApprovedApplication;
import com.example.demo.entity.RoomList;
import com.example.demo.repository.RoomListRepository;
import com.example.demo.service.ApprovedApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class ApprovedApplicationController {

    @Autowired
    private ApprovedApplicationService approvedApplicationService;

    @Autowired
    private RoomListRepository roomListRepository;
    /**
     * Hiển thị danh sách các ApprovedApplication kèm bộ lọc.
     */
    @GetMapping("/approved-applications")
    public String listApprovedApplications(
            @RequestParam(value = "applicationId", required = false) Long applicationId,
            @RequestParam(value = "submissionDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submissionDate,
            @RequestParam(value = "approvalDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvalDate,
            @RequestParam(value = "dormitoryArea", required = false) String dormitoryArea,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "department", required = false) String department,
            Model model
    ) {
        // Gọi service để lấy danh sách
        List<ApprovedApplication> applicationList = approvedApplicationService.getFilteredApplications(
                applicationId,
                submissionDate,
                approvalDate,
                dormitoryArea,
                address,
                department
        );

        model.addAttribute("applicationList", applicationList);


        List<RoomList> roomList = roomListRepository.findAll();
        model.addAttribute("roomList", roomList);


        model.addAttribute("departments", Arrays.asList(
                "CNTT", "KinhTe", "XHNV",
                "BK",      // Trường Bách khoa
                "SP",      // Trường Sư phạm
                "TS",      // Trường Thủy sản
                "NN",      // Trường Nông nghiệp
                "DBDT",    // Khoa Dự bị Dân tộc
                "GDTC",    // Khoa Giáo dục Thể chất
                "KHCT",    // Khoa Khoa học Chính trị
                "KHTN",    // Khoa Khoa học Tự nhiên
                "LU",      // Khoa Luật
                "MT&TNTN", // Khoa Môi trường & Tài nguyên Thiên nhiên
                "PTNT",    // Khoa Phát triển Nông thôn
                "SDH"      // Khoa Sau đại học
        ));

        List<String> dormitoryAreasList = Arrays.asList("KTX Khu A", "KTX Khu B");
        model.addAttribute("dormitoryAreas", dormitoryAreasList);

        return "manager/application/approved_application_list";
        // trả về file HTML trong thư mục templates/manager/application/approved_application_list.html
    }
}
