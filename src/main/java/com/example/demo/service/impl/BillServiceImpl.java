//filepath: src/main/java/com/example/demo/service/impl/BillServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.dto.BillFilterRequest;
import com.example.demo.dto.BillFilterRequestForManager;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.BillService;
import com.example.demo.service.EmailService;
import com.example.demo.specifications.BillSpecificationForManager;
import com.example.demo.specifications.BillSpecificationForStudent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;
    private final NotificationRepository notificationRepository;

    private final StudentRepository studentRepository;
    private final StudentServiceRegistrationRepository registrationRepository;

    private final EmailService emailService;
    private final DormitoryServiceRepository dormitoryServiceRepository;
    @Autowired
    public BillServiceImpl(BillRepository billRepository, RoomAssignmentRepository roomAssignmentRepository, NotificationRepository notificationRepository, StudentServiceRegistrationRepository registrationRepository, StudentRepository studentRepository,
                           DormitoryServiceRepository dormitoryServiceRepository, EmailService emailService) {
        this.billRepository = billRepository;
        this.roomAssignmentRepository = roomAssignmentRepository;
        this.notificationRepository = notificationRepository;
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.dormitoryServiceRepository = dormitoryServiceRepository;
        this.emailService = emailService;

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

        BillItem billItem = BillItem.builder()
                .bill(bill)
                .service(dormitoryServiceRepository.findByServiceName("Phòng ở"))
                .amount(monthlyRent)
                .unitPrice(monthlyRent)
                .quantity(1)
                .build();

        bill.setBillItems(List.of(billItem));
        //set created date
        bill.setCreatedDate(LocalDateTime.now());

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
                Bill bill = createRoomBill(studentId);
                //gui email cho sinh viên
                if(                    bill.getStudent() != null) {
                    emailService.sendSimpleEmail(
                            bill.getStudent().getEmail(),
                            "Hóa đơn mới",
                            "Hóa đơn của bạn đã được tạo thành công với mã hóa đơn: " + bill.getBillId()
                    );
                }
            }
        }
    }

    /**
     * Tạo hóa đơn cho danh sách registrationId
     */
    @Override
    @Transactional
    public Bill createBillForRegistrations(List<Long> registrationIds) {
        // Sử dụng truy vấn tối ưu để lấy thông tin đăng ký cùng với sinh viên
        List<StudentServiceRegistration> registrations = registrationRepository.findAllByIdsWithStudent(registrationIds);

        if (registrations.isEmpty()) {
            // Nếu không tìm thấy đăng ký nào, trả về HTTP 404 NOT FOUND
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Không tìm thấy đăng ký nào với danh sách ID đã cung cấp.");
        }

        try {
            // Giả sử tất cả đăng ký đều của cùng một sinh viên, lấy thông tin từ đăng ký đầu tiên
            Student student = registrations.get(0).getStudent();

            Bill bill = Bill.builder()
                    .billingPeriod(LocalDate.now().toString())
                    .issueDate(LocalDate.now())
                    .dueDate(LocalDate.now().plusDays(15))
                    .status(Bill.BillStatus.unpaid)
                    .student(student)
                    .billType(Bill.BillType.DICH_VU)
                    .build();

            List<BillItem> billItems = registrations.stream().map(registration ->
                    BillItem.builder()
                            .bill(bill)
                            .registration(registration)
                            .service(registration.getDormitoryService())
                            .amount(registration.getDormitoryService().getUnitPrice()
                                    * (registration.getActualQuantity() != null ? registration.getActualQuantity() : 1))
                            .unitPrice(registration.getDormitoryService().getUnitPrice())
                            .quantity(registration.getActualQuantity() != null ? registration.getActualQuantity() : 1)
                            .build()
            ).collect(Collectors.toList());

            bill.setBillItems(billItems);
            bill.calculateTotalAmount();
            //set created date
            bill.setCreatedDate(LocalDateTime.now());

            Notification notification = Notification.builder()
                    .student(student)
                    .title("Thông báo hóa đơn dịch vụ mới")
                    .message("Hóa đơn dịch vụ mới đã được tạo. Tổng tiền: " + bill.getTotalAmount() + " VND. Hạn đóng: " + bill.getDueDate())
                    .createdAt(LocalDateTime.now())
                    .readStatus(Notification.ReadStatus.unread)
                    .build();
            notificationRepository.save(notification);  // Lưu thông báo

            // Đánh dấu các đăng ký đã được tạo hóa đơn
            registrations.forEach(registration -> {
                registration.setInvoiced(StudentServiceRegistration.InvoicedStatus.YES);
                registrationRepository.save(registration);
            });

            return billRepository.save(bill);
        } catch (Exception e) {
            // Nếu xảy ra lỗi trong quá trình xử lý (database hay logic), trả về HTTP 500 INTERNAL SERVER ERROR
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi tạo hóa đơn: " + e.getMessage());
        }
    }

    public List<Bill> getBillsByStudentId(Long studentId) {
        // Kiểm tra xem sinh viên có tồn tại không
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với ID: " + studentId));

        // Lấy danh sách hóa đơn của sinh viên
        List<Bill> bills = billRepository.findByStudentStudentId(studentId);

        return bills;
    }

    public Page<Bill> getBillsByFilter(BillFilterRequest filterRequest) {
        // Tạo Specification dựa trên filter
        var spec = BillSpecificationForStudent.filter(
                filterRequest.getStudentId(),
                filterRequest.getRoomId(),
                filterRequest.getStatus(),
                filterRequest.getBillType(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate()
        );

        // Tạo Pageable
        var pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());

        // Gọi findAll(spec, pageable)
        return billRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Bill> getBillsByFilterForanager(BillFilterRequestForManager filterRequest) {
        var spec = BillSpecificationForManager.filter(
                filterRequest.getBillId(),
                filterRequest.getStudentId(),
                filterRequest.getStatus(),
                filterRequest.getBillType(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate()
        );

        var pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());
        return billRepository.findAll(spec, pageable);
    }


    @Override
    public Bill updateBillStatus(Long billId, Bill.BillStatus status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id: " + billId));
        bill.setStatus(status);
        return billRepository.save(bill);
    }

}
