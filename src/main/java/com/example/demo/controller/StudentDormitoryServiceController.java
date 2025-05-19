package com.example.demo.controller;

import com.example.demo.dto.DormitoryServiceDTO;
import com.example.demo.entity.DormitoryService;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.repository.DormitoryServiceRepository;
import com.example.demo.service.DormitoryServiceService;
import com.example.demo.service.StudentService;
import com.example.demo.service.StudentServiceRegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentDormitoryServiceController {
    @Autowired
    private DormitoryServiceService dormitoryServiceService;

    @Autowired
    private StudentServiceRegistrationService studentServiceRegistrationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    DormitoryServiceRepository dormitoryServiceRepository;



    @GetMapping("/services")
    public String listServices(Model model, HttpSession session) {
        List<DormitoryServiceDTO> services = dormitoryServiceService.getAllServices();
        Long studentId = studentService.getCurrentStudentId();
        model.addAttribute("services", services);

        // Lấy thông tin sinh viên đầy đủ và đưa vào session (nếu chưa có)
        if (session.getAttribute("student") == null) {
            Student student = studentService.getCurrentStudent(); // hoặc lấy từ context đăng nhập
            session.setAttribute("student", student);
        }

        return "student/service/services";
    }


    @PostMapping("/register-service/{serviceId}")
    @ResponseBody
    public ResponseEntity<String> registerService(@PathVariable Long serviceId) {
        try {
            Long studentId = studentService.getCurrentStudentId();
            StudentServiceRegistration registration = new StudentServiceRegistration();
            registration.setStartDate(LocalDate.now());
            registration.setStatus(StudentServiceRegistration.RegistrationStatus.pending);

            Optional<DormitoryService> serviceOpt = dormitoryServiceRepository.findById(serviceId);
            if(serviceOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dịch vụ không tồn tại");
            }

            DormitoryService service = serviceOpt.get();
            registration.setStudent(studentService.getStudentById(studentId));
            registration.setDormitoryService(service);
            registration.setInvoiced(StudentServiceRegistration.InvoicedStatus.NO);

            studentServiceRegistrationService.registerService(registration);
            return ResponseEntity.ok("Đăng ký thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng ký dịch vụ");
        }
    }

}
