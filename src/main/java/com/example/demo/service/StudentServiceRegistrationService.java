package com.example.demo.service;

import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.repository.StudentServiceRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceRegistrationService {

    @Autowired
    private StudentServiceRegistrationRepository studentServiceRegistrationRepository;

    public void registerService(StudentServiceRegistration registration) {
        studentServiceRegistrationRepository.save(registration);
    }
}