package com.example.demo.service.impl;

import com.example.demo.entity.BillingSchedule;
import com.example.demo.repository.BillingScheduleRepository;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillSchedulerService {

    private final BillingScheduleRepository billingScheduleRepository;
    private final BillService billService;

    @Autowired
    public BillSchedulerService(BillingScheduleRepository billingScheduleRepository, BillService billService) {
        this.billingScheduleRepository = billingScheduleRepository;
        this.billService = billService;
    }

    // Phương thức này chạy định kỳ, ví dụ mỗi phút để kiểm tra lịch hẹn giờ
    @Scheduled(fixedRate = 60000)
    public void processBillingSchedules() {
        LocalDateTime now = LocalDateTime.now();
        // Lấy các lịch có schedule_time <= hiện tại và đang active
        List<BillingSchedule> schedules = billingScheduleRepository.findByScheduleTimeLessThanEqualAndActive(now, true);
        for (BillingSchedule schedule : schedules) {
            // Ví dụ: tạo hóa đơn cho tất cả sinh viên có phân phòng đang active
            billService.createRoomBillForAll();
            // Sau khi xử lý, đánh dấu lịch đã được xử lý để không tạo lại hóa đơn
            schedule.setActive(false);
            billingScheduleRepository.save(schedule);
        }
    }
}
