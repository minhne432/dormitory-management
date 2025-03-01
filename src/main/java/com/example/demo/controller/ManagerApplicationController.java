package com.example.demo.controller;

import com.example.demo.service.ApplicationService;
import com.example.demo.service.StudentServiceRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/manager/applications")
public class ManagerApplicationController {



    private final StudentServiceRegistrationService studentServiceRegistrationService;
    @Autowired
    public ManagerApplicationController( StudentServiceRegistrationService studentServiceRegistrationService) {
        this.studentServiceRegistrationService = studentServiceRegistrationService;

    }

    @PostMapping("/{applicationId}/update-actual-quantity")
    public Map<String, String> updateActualQuantity(@PathVariable Long applicationId,
                                                    @RequestParam("actualQuantity") Integer actualQuantity) {
        try {
            studentServiceRegistrationService.updateActualQuantity(applicationId, actualQuantity);
            return Collections.singletonMap("message", "Cập nhật actualQuantity thành công!");
        } catch (Exception e) {
            return Collections.singletonMap("error", e.getMessage());
        }
    }
}
