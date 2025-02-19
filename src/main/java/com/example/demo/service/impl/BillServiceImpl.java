package com.example.demo.service.impl;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Notification;
import com.example.demo.entity.RoomAssignment;
import com.example.demo.entity.Room;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.RoomAssignmentRepository;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;
    private final NotificationRepository notificationRepository;
    @Autowired
    public BillServiceImpl(BillRepository billRepository, RoomAssignmentRepository roomAssignmentRepository, NotificationRepository notificationRepository) {
        this.billRepository = billRepository;
        this.roomAssignmentRepository = roomAssignmentRepository;
        this.notificationRepository = notificationRepository;

    }

    @Override
    public Bill createRoomBill(Long studentId) {
        // Lấy thông tin phân phòng hiện hành của sinh viên
        RoomAssignment assignment = roomAssignmentRepository.findByStudentStudentIdAndEndDateIsNull(studentId);
        if (assignment == null) {
            throw new RuntimeException("Không tìm thấy phân phòng hiện tại cho sinh viên có id: " + studentId);
        }

        // Lấy thông tin phòng và loại phòng
        Room room = assignment.getRoom();
        if (room == null || room.getRoomType() == null) {
            throw new RuntimeException("Thông tin phòng hoặc loại phòng không hợp lệ.");
        }
        double monthlyRent = room.getRoomType().getPrice(); // Giá phòng theo tháng

        // Xác định ngày tạo hóa đơn:
        // - issueDate: ngày hiện tại
        // - startDate: ngày đầu tháng (sử dụng cho tính toán, nếu cần)
        // - dueDate: đầu tháng tiếp theo
        LocalDate issueDate = LocalDate.now();
        LocalDate startDate = issueDate.withDayOfMonth(1);
        LocalDate dueDate = startDate.plusMonths(1);

        Bill bill = Bill.builder()
                .student(assignment.getStudent())
                .room(room)
                .billType(Bill.BillType.PHONG)
                .totalAmount(monthlyRent)
                .issueDate(issueDate)
                .dueDate(dueDate)
                .status(Bill.BillStatus.unpaid)
                .build();

String title = "Thông báo hóa đơn tiền phòng mới";
String message = String.format("Hóa đơn tiền phòng %s cho tháng %d/%d đã được tạo. Tổng tiền: %.0f VND. Hạn đóng: %s",
        room.getRoomNumber(),
        issueDate.getMonthValue(),
        issueDate.getYear(),
        monthlyRent,
        dueDate.toString());

Notification notification = Notification.builder()
        .student(assignment.getStudent())
        .title(title)
        .message(message)
        .createdAt(LocalDateTime.now())
        .readStatus(Notification.ReadStatus.unread)
        .build();
notificationRepository.save(notification);

        return billRepository.save(bill);
    }

    @Override
    public boolean billExistsForStudent(Long studentId, LocalDate monthStart) {
        // Kiểm tra xem sinh viên đã có hóa đơn cho tháng hiện tại chưa
        List<Bill> bills = billRepository.findByStudentStudentIdAndIssueDateBetween(
                studentId,
                monthStart,
                monthStart.plusMonths(1)
        );
        return bills != null && !bills.isEmpty();
    }

    @Override
    public void createRoomBillForAll() {
        // Lấy danh sách tất cả các phân phòng đang active (chưa có endDate)
        List<RoomAssignment> activeAssignments = roomAssignmentRepository.findAllByEndDateIsNull();
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        for (RoomAssignment assignment : activeAssignments) {
            Long studentId = assignment.getStudent().getStudentId();
            if (!billExistsForStudent(studentId, monthStart)) {
                createRoomBill(studentId);
            }
        }
    }
}
