package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.DormitoryService.ServiceType;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ServiceUsageRepository;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.NotificationService;
import com.example.demo.service.StudentService;
import com.example.demo.service.UtilityBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UtilityBillingServiceImpl implements UtilityBillingService {

    private final RoomRepository roomRepository;
    private final ServiceUsageRepository serviceUsageRepository;
    private final BillRepository billRepository;
    private final StudentService studentService;
    private final NotificationService notificationService;

    @Autowired
    public UtilityBillingServiceImpl(RoomRepository roomRepository,
                                     ServiceUsageRepository serviceUsageRepository,
                                     BillRepository billRepository,
                                     StudentService studentService,
                                     NotificationService notificationService) {
        this.roomRepository = roomRepository;
        this.serviceUsageRepository = serviceUsageRepository;
        this.billRepository = billRepository;
        this.studentService = studentService;
        this.notificationService = notificationService;
    }

    @Override
    public void processUtilityBillingForAllRooms() {
        // Xác định kỳ tính tiền: xử lý số liệu của tháng trước
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        LocalDate periodStart = previousMonth.withDayOfMonth(1);
        LocalDate periodEnd = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String billingPeriod = previousMonth.format(formatter);

        // Lấy tất cả các phòng và xử lý những phòng có người ở (currentOccupancy > 0)
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            if (room.getCurrentOccupancy() != null && room.getCurrentOccupancy() > 0) {
                // Lấy các bản ghi ServiceUsage của phòng trong kỳ này, chưa xuất hóa đơn
                List<ServiceUsage> usages = serviceUsageRepository.findByRoomAndRecordDateBetweenAndInvoiced(
                        room, periodStart, periodEnd, ServiceUsage.InvoicedStatus.NO);
                // Lọc ra chỉ những bản ghi ứng với dịch vụ điện hoặc nước
                List<ServiceUsage> utilityUsages = new ArrayList<>();
                for (ServiceUsage usage : usages) {
                    String sName = usage.getService().getServiceName();
                    if (sName != null && (sName.equalsIgnoreCase("Điện") || sName.equalsIgnoreCase("Nước"))) {
                        utilityUsages.add(usage);
                    }
                }
                if (utilityUsages.isEmpty()) {
                    continue; // Không có số liệu cần xử lý cho phòng này
                }
                // Nhóm các bản ghi theo dịch vụ (giả sử mỗi phòng chỉ có 1 bản ghi cho từng dịch vụ trong 1 kỳ)
                Map<Long, List<ServiceUsage>> usageGroup = new HashMap<>();
                for (ServiceUsage usage : utilityUsages) {
                    Long serviceId = usage.getService().getServiceId();
                    usageGroup.computeIfAbsent(serviceId, k -> new ArrayList<>()).add(usage);
                }
                // Tạo danh sách BillItem và tính tổng số tiền của hóa đơn
                List<BillItem> billItems = new ArrayList<>();
                double totalAmount = 0.0;
                for (Map.Entry<Long, List<ServiceUsage>> entry : usageGroup.entrySet()) {
                    List<ServiceUsage> groupUsages = entry.getValue();
                    double consumptionSum = 0.0;
                    // Tính tổng mức tiêu thụ của nhóm: (current - previous)
                    for (ServiceUsage usage : groupUsages) {
                        double consumption = usage.getCurrentReading() - usage.getPreviousReading();
                        consumptionSum += consumption;
                    }
                    DormitoryService service = groupUsages.get(0).getService();
                    double unitPrice = service.getUnitPrice();
                    double cost = consumptionSum * unitPrice;
                    totalAmount += cost;

                    BillItem billItem = BillItem.builder()
                            .service(service)
                            .amount(cost)
                            .quantity((int) consumptionSum)
                            .unitPrice(unitPrice)
                            .build();
                    billItems.add(billItem);
                }
                // Tạo hóa đơn với bill_type = DIEN_NUOC
                Bill bill = Bill.builder()
                        .billingPeriod(billingPeriod)
                        .issueDate(now)
                        // Đặt ngày đến hạn thanh toán là 10 ngày sau ngày tạo hóa đơn
                        .dueDate(now.plusDays(10))
                        .status(Bill.BillStatus.unpaid)
                        .totalAmount(totalAmount)
                        .billType(Bill.BillType.DIEN_NUOC)
                        .room(room)
                        // Hóa đơn điện nước không có student cụ thể nên để null
                        .student(null)
                        .build();
                // Liên kết các BillItem với hóa đơn
                for (BillItem item : billItems) {
                    item.setBill(bill);
                }
                bill.setBillItems(billItems);
                //set created date
                bill.setCreatedDate(LocalDateTime.now());
                Bill savedBill = billRepository.save(bill);

                // Cập nhật các bản ghi ServiceUsage đã xử lý: đánh dấu invoiced = YES và liên kết hóa đơn
                for (ServiceUsage usage : utilityUsages) {
                    usage.setInvoiced(ServiceUsage.InvoicedStatus.YES);
                    usage.setBill(savedBill);
                    serviceUsageRepository.save(usage);
                }

                // 🔹 **Gửi thông báo đến tất cả sinh viên trong phòng sau khi tạo hóa đơn thành công**
                List<Student> students = studentService.getStudentsByRoom(room);
                if (!students.isEmpty()) {
                    String title = "Hóa đơn điện nước tháng " + billingPeriod;
                    String message = "Hóa đơn phòng " + room.getRoomNumber() + " đã được tạo với số tiền: " + totalAmount + " VND. Vui lòng thanh toán trước ngày " + bill.getDueDate() + ".";

                    notificationService.sendNotificationToStudents(students, title, message);
                }
            }
        }
    }
}
