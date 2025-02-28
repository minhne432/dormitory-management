package com.example.demo.service;

import com.example.demo.entity.Dormitory;
import com.example.demo.repository.DormitoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryService {

    private final DormitoryRepository dormitoryRepository;

    public DormitoryService(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    // Phương thức lấy tất cả các Dormitory
    public List<Dormitory> getAllDormitories() {
        return dormitoryRepository.findAll();
    }
}
