package com.example.demo.service;

import com.example.demo.dto.RegistrationFilterDTO;
import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.repository.StudentServiceRegistrationRepository;
import com.example.demo.specifications.StudentServiceRegistrationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceRegistrationService {

    @Autowired
    private StudentServiceRegistrationRepository studentServiceRegistrationRepository;


    public void registerService(StudentServiceRegistration registration) {
        studentServiceRegistrationRepository.save(registration);
    }

    public StudentServiceRegistration updateRegistrationStatus(Long registrationId, StudentServiceRegistration.RegistrationStatus newStatus) {
        StudentServiceRegistration registration = studentServiceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Registration có id " + registrationId));

        // Chỉ cập nhật nếu trạng thái hiện tại là pending
        if (registration.getStatus() == StudentServiceRegistration.RegistrationStatus.pending) {
            registration.setStatus(newStatus);
            if (newStatus == StudentServiceRegistration.RegistrationStatus.approved) {
                registration.setApprovalDate(LocalDate.now());
                // Nếu có thông tin về người duyệt (Manager), có thể set ở đây
            }
            return studentServiceRegistrationRepository.save(registration);
        }
        throw new RuntimeException("Không thể cập nhật yêu cầu. Trạng thái hiện tại: " + registration.getStatus());
    }

    public void updateActualQuantity(Long registrationId, Integer actualQuantity) {
        StudentServiceRegistration registration = studentServiceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Registration có id " + registrationId));
        registration.setActualQuantity(actualQuantity);
        studentServiceRegistrationRepository.save(registration);
    }

    public List<StudentServiceRegistration> getRegistrationsByStudentAndFilter(Long studentId, RegistrationFilterDTO filter) {
        // Sử dụng Specification để lọc theo studentId, dormitoryServiceId và status
        return studentServiceRegistrationRepository.findAll(
                StudentServiceRegistrationSpecification.filter(filter, studentId),
                Sort.by("startDate").descending()
        );
    }
}
