package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.NotEnoughCapacityException;
import com.example.demo.repository.*;
import com.example.demo.specifications.RoomAssignmentSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomAssignmentService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ApprovedApplicationRepository approvedApplicationRepository;

    @Autowired
    private StudentRepository studentRepository; // Để findById(studentId)

    @Autowired
    private RoomAssignmentRepository roomAssignmentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    EmailService emailService; // Để gửi email
    @Autowired ApplicationService applicationService;

    public void assignRoomToApplications(Long roomId, List<Long> applicationIds) {
        // 1. Lấy Room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Room với ID: " + roomId));

        // 2. Kiểm tra sức chứa
        int currentOccupancy = room.getCurrentOccupancy() != null ? room.getCurrentOccupancy() : 0;
        int maxCapacity = 0;
        if (room.getRoomType() != null) {
            // Nếu roomType có capacity, bạn có logic khác
            // Còn không, ta dùng trường roomListView
            // Hoặc bạn có cột "room_max_capacity" trực tiếp trong Room
            // Tùy theo DB
        }
        // Nếu trong bảng ROOMS bạn có cột room_max_capacity, ta get:
        // int maxCapacity = room.getRoomMaxCapacity();
        // Hoặc tạm thời fix cứng/nếu DB chưa có
        int needed = applicationIds.size();

        // Ở đây, giả sử bạn đã có maxCapacity trong Room,
        // nếu không thì phải join sang RoomType, v.v.
        int capacity = room.getRoomType() != null
                ? room.getRoomType().getMaxCapacity()  // giả sử cột maxCapacity ở RoomType
                : 0;
        // Hoặc:
        // int capacity = room.getRoomMaxCapacity(); // nếu cột này có thật
        if (capacity == 0) {
            // fallback
            capacity = 9999;
        }

        int remaining = capacity - currentOccupancy;
        if (needed > remaining) {
            throw new NotEnoughCapacityException(
                    "Số lượng sinh viên chọn (" + needed +
                            ") vượt quá sức chứa còn lại của phòng (" + remaining + ")"
            );
        }

        // 3. Lặp qua applicationIds -> tạo RoomAssignment và Notification
        LocalDate now = LocalDate.now();
        for (Long appId : applicationIds) {
            // Tìm ApprovedApplication
            ApprovedApplication app = approvedApplicationRepository.findById(appId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ApprovedApplication ID = " + appId));

            // Lấy Student
            Long studentId = app.getStudentId();
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Student ID = " + studentId));

            // Tạo RoomAssignment
            RoomAssignmentId raId = new RoomAssignmentId(roomId, studentId);
            RoomAssignment assignment = new RoomAssignment();
            assignment.setId(raId);
            assignment.setRoom(room);
            assignment.setStudent(student);
            assignment.setAssignedDate(now);

            // Lưu RoomAssignment
            roomAssignmentRepository.save(assignment);


            //send email
            String email = student.getEmail();
            String subject = "Room Assignment Notification";
            String body = "Bạn đã được xếp vào phòng " + room.getRoomNumber() + " kể từ ngày " + now;

            if(email!= null && !email.isEmpty()){
                emailService.sendSimpleEmail(email, subject, body);
            }

            // Tạo Notification
            Notification notification = new Notification();
            notification.setStudent(student);
            notification.setTitle("Room Assigned");
            notification.setMessage("Bạn đã được xếp vào phòng "
                    + room.getRoomNumber() + " kể từ ngày " + now);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setReadStatus(Notification.ReadStatus.unread);
            notificationRepository.save(notification);

            //cập nhật application status là completed
            applicationService.completeApplication(appId);
        }

        // 4. Cập nhật currentOccupancy
        room.setCurrentOccupancy(currentOccupancy + needed);

        // Bạn có thể set lại status phòng nếu đầy
        if ((currentOccupancy + needed) >= capacity) {
            room.setStatus(Room.RoomStatus.full);
        } else {
            room.setStatus(Room.RoomStatus.available);
        }

        // Lưu Room
        roomRepository.save(room);
    }

    /** Lấy danh sách RoomAssignment đang hoạt động, có filter + phân trang */
    public Page<RoomAssignment> getActiveAssignments(Long studentId,
                                                     Long roomId,
                                                     int page, int size) {
        var spec = RoomAssignmentSpecification.filter(studentId, roomId);
        return roomAssignmentRepository.findAll(spec, PageRequest.of(page, size));
    }

    /** Huỷ xếp phòng (set endDate = today, giảm occupancy & cập nhật status) */
    @Transactional
    public void cancelAssignment(RoomAssignmentId id) {
        RoomAssignment ra = roomAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        if (ra.getEndDate() != null) return; // đã kết thúc

        // 1. set endDate
        ra.setEndDate(LocalDate.now());

        // 2. giảm occupancy & cập nhật status phòng
        Room room = ra.getRoom();
        int newOcc = Math.max(0, room.getCurrentOccupancy() - 1);
        room.setCurrentOccupancy(newOcc);

        if (room.getStatus() == Room.RoomStatus.full && newOcc < room.getRoomType().getMaxCapacity()) {
            room.setStatus(Room.RoomStatus.available);
        }

        // 3. lưu
        roomAssignmentRepository.save(ra);
        roomRepository.save(room);
    }
}
