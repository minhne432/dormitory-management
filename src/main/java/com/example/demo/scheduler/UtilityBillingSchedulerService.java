package com.example.demo.scheduler;

import com.example.demo.entity.UtilityBillingSchedule;
import com.example.demo.repository.UtilityBillingScheduleRepository;
import com.example.demo.service.UtilityBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UtilityBillingSchedulerService {

    private final UtilityBillingScheduleRepository scheduleRepository;
    private final UtilityBillingService utilityBillingService;

    @Autowired
    public UtilityBillingSchedulerService(UtilityBillingScheduleRepository scheduleRepository,
                                          UtilityBillingService utilityBillingService) {
        this.scheduleRepository = scheduleRepository;
        this.utilityBillingService = utilityBillingService;
    }

    // Chạy định kỳ mỗi phút để kiểm tra các lịch hẹn giờ trong DB
    @Scheduled(fixedRate = 60000)
    public void processUtilityBillingSchedules() {
        LocalDateTime now = LocalDateTime.now();
        List<UtilityBillingSchedule> schedules = scheduleRepository.findByScheduleTimeLessThanEqualAndActive(now, true);
        for (UtilityBillingSchedule schedule : schedules) {
            // Gọi xử lý tính hóa đơn điện nước cho tất cả các phòng có người ở
            utilityBillingService.processUtilityBillingForAllRooms();
            // Đánh dấu lịch đã được xử lý để không thực hiện lại
            schedule.setActive(false);
            scheduleRepository.save(schedule);
        }
    }
}
