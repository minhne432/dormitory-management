package com.example.demo.dto;

import java.time.LocalDate;

public class ServiceUsageStatistic {
    private LocalDate date;
    private String serviceName; // Ví dụ: "Điện", "Nước", "Giặt ủi", "Gửi xe",...
    private Double usageCount;  // Sử dụng Double để tính tiêu thụ

    public ServiceUsageStatistic() {}

    public ServiceUsageStatistic(LocalDate date, String serviceName, Double usageCount) {
        this.date = date;
        this.serviceName = serviceName;
        this.usageCount = usageCount;
    }

    // Getters & Setters

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public Double getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(Double usageCount) {
        this.usageCount = usageCount;
    }
}
