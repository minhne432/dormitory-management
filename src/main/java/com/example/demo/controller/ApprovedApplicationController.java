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

        List<String> departments = Arrays.asList("CNTT", "KinhTe","XHNV");
        model.addAttribute("departments", departments);

        List<String> dormitoryAreasList = Arrays.asList("KTX Khu A", "KTX Khu B");
        model.addAttribute("dormitoryAreas", dormitoryAreasList);

        return "manager/application/approved_application_list";
        // trả về file HTML trong thư mục templates/manager/application/approved_application_list.html
    }
}
