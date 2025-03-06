package com.example.demo.service;

import com.example.demo.dto.DormApplicationForm;
import com.example.demo.entity.Application;
import com.example.demo.entity.Student;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ApplicationService {

    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationService(StudentRepository studentRepository,
                              ApplicationRepository applicationRepository) {
        this.studentRepository = studentRepository;
        this.applicationRepository = applicationRepository;
    }

    public DormApplicationForm prepareDormApplicationForm(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new IllegalArgumentException("Student not found!");
        }

        Student student = optionalStudent.get();
        DormApplicationForm form = new DormApplicationForm();
        form.setFullName(student.getFullName());
        form.setDepartment(student.getDepartment());
        form.setStudentClass(student.getStudentClass());
        form.setPhoneNumber(student.getPhoneNumber());
        form.setEmail(student.getEmail());
        form.setAddress(student.getAddress());

        return form;
    }

    public void saveDormApplication(Long studentId, DormApplicationForm form) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new IllegalArgumentException("Student not found!");
        }

        Student student = optionalStudent.get();
        Application application = new Application();
        application.setStudent(student);
        application.setSubmissionDate(LocalDate.now());
        application.setStatus(Application.ApplicationStatus.pending);
        application.setDormitoryArea(form.getDormitoryArea());
        application.setNote(form.getNote());

        applicationRepository.save(application);
    }

    /**
     * Tìm kiếm Application theo ID và cập nhật trạng thái thành ApplicationStatus.completed.
     *
     * @param applicationId ID của Application cần cập nhật.
     * @throws IllegalArgumentException nếu Application không tồn tại.
     */
    public void completeApplication(Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);

        if (optionalApplication.isEmpty()) {
            throw new IllegalArgumentException("Application not found!");
        }

        Application application = optionalApplication.get();
        application.setStatus(Application.ApplicationStatus.completed);

        // Lưu cập nhật vào cơ sở dữ liệu
        applicationRepository.save(application);
    }

    public boolean hasApplicationWithStudentId(Long studentId) {
        return applicationRepository.existsByStudent_StudentId(studentId);
    }

}
