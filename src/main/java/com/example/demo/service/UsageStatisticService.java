package com.example.demo.service;

import com.example.demo.dto.ServiceUsageStatistic;
import com.example.demo.entity.DormitoryService;
import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.StudentServiceRegistration;
import com.example.demo.repository.ServiceUsageRepository;
import com.example.demo.repository.StudentServiceRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsageStatisticService {

    @Autowired
    private ServiceUsageRepository serviceUsageRepository;

    @Autowired
    private StudentServiceRegistrationRepository studentServiceRegistrationRepository;

    // Thống kê dịch vụ phòng (ví dụ: điện, nước)
    public List<ServiceUsageStatistic> getRoomUsageStatistics(LocalDate startDate, LocalDate endDate) {
        List<ServiceUsage> roomUsages = serviceUsageRepository.findByRecordDateBetween(startDate, endDate)
                .stream()
                .filter(u -> u.getService().getServiceType() == DormitoryService.ServiceType.ROOM)
                .collect(Collectors.toList());

        Map<String, Double> statisticMap = new HashMap<>();

        for (ServiceUsage usage : roomUsages) {
            LocalDate date = usage.getRecordDate();
            String serviceName = usage.getService().getServiceName();
            double consumption = usage.getCurrentReading() - usage.getPreviousReading();
            String key = date.toString() + "_" + serviceName;
            statisticMap.put(key, statisticMap.getOrDefault(key, 0.0) + consumption);
        }

        List<ServiceUsageStatistic> stats = new ArrayList<>();
        for (Map.Entry<String, Double> entry : statisticMap.entrySet()) {
            String[] parts = entry.getKey().split("_");
            LocalDate date = LocalDate.parse(parts[0]);
            String serviceName = parts[1];
            stats.add(new ServiceUsageStatistic(date, serviceName, entry.getValue()));
        }
        stats.sort(Comparator.comparing(ServiceUsageStatistic::getDate));
        return stats;
    }

    // Thống kê dịch vụ cá nhân (ví dụ: giặt ủi, gửi xe)
    public List<ServiceUsageStatistic> getPersonalUsageStatistics(LocalDate startDate, LocalDate endDate) {
        List<StudentServiceRegistration> personalRegs = studentServiceRegistrationRepository.findByStartDateBetween(startDate, endDate)
                .stream()
                .filter(r -> r.getDormitoryService().getServiceType() == DormitoryService.ServiceType.PERSONAL)
                .collect(Collectors.toList());

        Map<String, Double> statisticMap = new HashMap<>();

        for (StudentServiceRegistration reg : personalRegs) {
            LocalDate date = reg.getStartDate(); // Hoặc thay bằng approvalDate nếu cần
            String serviceName = reg.getDormitoryService().getServiceName();
            double qty = reg.getActualQuantity() != null ? reg.getActualQuantity() : 0.0;
            String key = date.toString() + "_" + serviceName;
            statisticMap.put(key, statisticMap.getOrDefault(key, 0.0) + qty);
        }

        List<ServiceUsageStatistic> stats = new ArrayList<>();
        for (Map.Entry<String, Double> entry : statisticMap.entrySet()) {
            String[] parts = entry.getKey().split("_");
            LocalDate date = LocalDate.parse(parts[0]);
            String serviceName = parts[1];
            stats.add(new ServiceUsageStatistic(date, serviceName, entry.getValue()));
        }
        stats.sort(Comparator.comparing(ServiceUsageStatistic::getDate));
        return stats;
    }
}
