package com.example.demo.controller;

import com.example.demo.dto.DormitoryServiceDTO;
import com.example.demo.service.DormitoryServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/manager/services")
public class DormitoryServiceController {

    @Autowired
    private DormitoryServiceService dormitoryServiceService;


    // Hiển thị danh sách dịch vụ
    @GetMapping
    public String listServices(Model model) {
        List<DormitoryServiceDTO> services = dormitoryServiceService.getAllServicesForAdmin();
        model.addAttribute("services", services);
        return "manager/service/list-services";
    }

    // Hiển thị form thêm dịch vụ mới
    @GetMapping("/add")
    public String showAddServiceForm(Model model) {
        model.addAttribute("serviceDTO", new DormitoryServiceDTO());
        return "manager/service/add-service";
    }

    // Xử lý form thêm dịch vụ mới
    @PostMapping("/add")
    public String addService(@Valid @ModelAttribute("serviceDTO") DormitoryServiceDTO serviceDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "manager/add-service"; // Quay lại form nếu có lỗi
        }
        try {
            dormitoryServiceService.createService(serviceDTO);
            model.addAttribute("successMessage", "Dịch vụ đã được thêm thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Dịch vụ đã tồn tại hoặc có lỗi xảy ra!");
        }
        return "manager/service/add-service";
    }

    @GetMapping("/edit/{id}")
    public String showEditServiceForm(@PathVariable("id") Long id, Model model) {
        try {
            DormitoryServiceDTO serviceDTO = dormitoryServiceService.getServiceById(id);
            model.addAttribute("serviceDTO", serviceDTO);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/dormitory-services/list"; // Chuyển hướng nếu không tìm thấy dịch vụ
        }
        return "manager/service/edit-service";
    }

    @PostMapping("/edit/{id}")
    public String updateService(@PathVariable("id") Long id,
                                @ModelAttribute("serviceDTO") @Valid DormitoryServiceDTO serviceDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "manager/edit-service";
        }
        try {
            dormitoryServiceService.updateService(id, serviceDTO);
            model.addAttribute("successMessage", "Cập nhật dịch vụ thành công!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "manager/service/edit-service";
    }
    //Student


}
