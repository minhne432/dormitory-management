package com.example.demo.service;

import com.example.demo.dto.DormitoryServiceDTO;
import com.example.demo.entity.DormitoryService;
import com.example.demo.repository.DormitoryServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DormitoryServiceService {

    @Autowired
    private DormitoryServiceRepository dormitoryServiceRepository;

    @Transactional
    public DormitoryServiceDTO createService(DormitoryServiceDTO serviceDTO) {
        if (dormitoryServiceRepository.existsByServiceName(serviceDTO.getServiceName())) {
            throw new RuntimeException("Dịch vụ này đã tồn tại!");
        }

        DormitoryService newService = DormitoryService.builder()
                .serviceName(serviceDTO.getServiceName())
                .unitPrice(serviceDTO.getUnitPrice())
                .unit(serviceDTO.getUnit())
                .description(serviceDTO.getDescription())
                .build();

        DormitoryService savedService = dormitoryServiceRepository.save(newService);

        return DormitoryServiceDTO.builder()
                .serviceId(savedService.getServiceId())
                .serviceName(savedService.getServiceName())
                .unitPrice(savedService.getUnitPrice())
                .unit(savedService.getUnit())
                .description(savedService.getDescription())
                .build();
    }

    @Transactional
    public DormitoryServiceDTO updateService(Long serviceId, DormitoryServiceDTO serviceDTO) {
        Optional<DormitoryService> optionalService = dormitoryServiceRepository.findById(serviceId);
        if (optionalService.isEmpty()) {
            throw new RuntimeException("Dịch vụ không tồn tại hoặc không hợp lệ!");
        }

        DormitoryService existingService = optionalService.get();
        existingService.setServiceName(serviceDTO.getServiceName());
        existingService.setUnitPrice(serviceDTO.getUnitPrice());
        existingService.setUnit(serviceDTO.getUnit());
        existingService.setDescription(serviceDTO.getDescription());

        DormitoryService updatedService = dormitoryServiceRepository.save(existingService);

        return DormitoryServiceDTO.builder()
                .serviceId(updatedService.getServiceId())
                .serviceName(updatedService.getServiceName())
                .unitPrice(updatedService.getUnitPrice())
                .unit(updatedService.getUnit())
                .description(updatedService.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public DormitoryServiceDTO getServiceById(Long serviceId) {
        return dormitoryServiceRepository.findById(serviceId)
                .map(service -> DormitoryServiceDTO.builder()
                        .serviceId(service.getServiceId())
                        .serviceName(service.getServiceName())
                        .unitPrice(service.getUnitPrice())
                        .unit(service.getUnit())
                        .description(service.getDescription())
                        .build())
                .orElseThrow(() -> new RuntimeException("Dịch vụ không tồn tại hoặc không hợp lệ!"));
    }

    // Lấy danh sách tất cả dịch vụ
    @Transactional(readOnly = true)
    public List<DormitoryServiceDTO> getAllServices() {
        return dormitoryServiceRepository.findByVisibleForStudentTrue()
                .stream()
                .map(service -> DormitoryServiceDTO.builder()
                        .serviceId(service.getServiceId())
                        .serviceName(service.getServiceName())
                        .unitPrice(service.getUnitPrice())
                        .unit(service.getUnit())
                        .description(service.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
