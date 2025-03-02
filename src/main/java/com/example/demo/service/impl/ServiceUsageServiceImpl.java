package com.example.demo.service.impl;

import com.example.demo.entity.ServiceUsage;
import com.example.demo.entity.Room;
import com.example.demo.entity.DormitoryService;
import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.Student;
import com.example.demo.repository.ServiceUsageRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.DormitoryServiceRepository;
import com.example.demo.repository.RoomAssignmentRepository;
import com.example.demo.service.ServiceUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceUsageServiceImpl implements ServiceUsageService {

    private final ServiceUsageRepository serviceUsageRepository;
    private final RoomRepository roomRepository;
    private final DormitoryServiceRepository dormitoryServiceRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;

    @Autowired
    public ServiceUsageServiceImpl(ServiceUsageRepository serviceUsageRepository,
                                   RoomRepository roomRepository,
                                   DormitoryServiceRepository dormitoryServiceRepository,
                                   RoomAssignmentRepository roomAssignmentRepository) {
        this.serviceUsageRepository = serviceUsageRepository;
        this.roomRepository = roomRepository;
        this.dormitoryServiceRepository = dormitoryServiceRepository;
        this.roomAssignmentRepository = roomAssignmentRepository;
    }

    @Override
    public ServiceUsage recordMeterReading(Long roomId, Long serviceId, Double currentReading, LocalDate recordDate) {
        // Lấy thông tin phòng theo roomId
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với id: " + roomId));

        // Lấy thông tin dịch vụ theo serviceId
        DormitoryService service = dormitoryServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với id: " + serviceId));

        // Kiểm tra xem đã ghi số cho phòng này, dịch vụ này vào ngày ghi số (thường là ngày 1 của tháng) chưa
        Optional<ServiceUsage> existingRecord = serviceUsageRepository.findByRoomAndServiceAndRecordDate(room, service, recordDate);
        if(existingRecord.isPresent()){
            throw new RuntimeException("Đã ghi số cho phòng này và dịch vụ này vào ngày: " + recordDate);
        }

        // Lấy số đo cũ từ bản ghi gần nhất (nếu có)
// Kiểm tra xem đã có ghi số trong tháng này chưa
Optional<ServiceUsage> lastRecordOpt = serviceUsageRepository.findTopByRoomAndServiceOrderByRecordDateDesc(room, service);
if (lastRecordOpt.isPresent() && lastRecordOpt.get().getRecordDate().getMonth() == recordDate.getMonth()
    && lastRecordOpt.get().getRecordDate().getYear() == recordDate.getYear()) {
    throw new RuntimeException("Đã ghi số cho phòng này và dịch vụ này trong tháng " +
        recordDate.getMonthValue() + "/" + recordDate.getYear());
}
Double previousReading = lastRecordOpt.map(ServiceUsage::getCurrentReading).orElse(0.0);


        // Tạo bản ghi ServiceUsage mới
        ServiceUsage usage = ServiceUsage.builder()
                .room(room)
                .service(service)
//                .student(student)
                .previousReading(previousReading)
                .currentReading(currentReading)
                .recordDate(recordDate)
                .invoiced(ServiceUsage.InvoicedStatus.NO)
                .build();

        return serviceUsageRepository.save(usage);
    }

    @Override
    public List<ServiceUsage> searchServiceUsages(Specification<ServiceUsage> spec) {
        return serviceUsageRepository.findAll(spec);
    }
}
