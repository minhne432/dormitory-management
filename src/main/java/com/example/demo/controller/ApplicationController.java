package com.example.demo.controller;

import com.example.demo.dto.DormApplicationForm;
import com.example.demo.repository.RoomListRepository;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.StudentService;
import com.example.demo.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final StudentService studentService;
    private final EmailService emailService;

    private final RoomListRepository roomListRepository;

    public ApplicationController(ApplicationService applicationService, StudentService studentService, EmailService emailService,
                                 RoomListRepository roomListRepository) {
        this.applicationService = applicationService;
        this.studentService = studentService;
        this.emailService = emailService;
        this.roomListRepository = roomListRepository;
    }

    @GetMapping("/register-dormitory")
    public String showDormForm(Model model) {
        Long currentStudentId = studentService.getCurrentStudentId();

        // Kiểm tra xem sinh viên đã có đơn đăng ký nào chưa
        boolean hasExistingApplication = applicationService.hasApplicationWithStudentId(currentStudentId);

        if (hasExistingApplication) {
            // Nếu đã có đơn đăng ký, chuyển hướng đến view thông báo
            return "student/application/applicationPending"; // Tên view Thymeleaf cho thông báo
        } else {
            // Nếu chưa có đơn đăng ký, hiển thị form đăng ký như bình thường
            DormApplicationForm form = applicationService.prepareDormApplicationForm(currentStudentId);
            model.addAttribute("student", form);
            model.addAttribute("rooms",    roomListRepository.findAll());
            return "student/application/registerDormitory"; // Tên view Thymeleaf cho form đăng ký
        }
    }

    @PostMapping("/register-dormitory")
    public String processDormRegistration(
            @ModelAttribute("student") DormApplicationForm form,
            Model model
    ) {
        try {
            Long currentStudentId = studentService.getCurrentStudentId();
            applicationService.saveDormApplication(currentStudentId, form);
            //send email notification

            if(studentService.getStudentById(currentStudentId).getEmail() != null) {
                emailService.sendSimpleEmail(
                        studentService.getStudentById(currentStudentId).getEmail(),
                        "Đăng ký Ký túc xá",
                        "Đơn đăng ký của bạn đã được gửi thành công. Chúng tôi sẽ xem xét và phản hồi trong thời gian sớm nhất!"
                );
            }



            return "redirect:/register-dormitory-success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "student/application/registerDormitory";
        }
    }

    @GetMapping("/register-dormitory-success")
    public String dormRegisterSuccess() {
        return "student/application/registerDormitorySuccess";
    }


}
