package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.specifications.RoomSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomSearchController {

    private final RoomRepository roomRepository;

    public RoomSearchController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/search")
    public List<RoomDTO> searchRooms(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "dormitoryName", required = false) String dormitoryName,
            @RequestParam(value = "minCapacity", required = false) Integer minCapacity,
            @RequestParam(value = "maxCapacity", required = false) Integer maxCapacity) {

        // Xây dựng Specification dựa trên các tham số lọc
        Specification<Room> spec = Specification.where(RoomSpecifications.hasAvailableStatus())
                .and(RoomSpecifications.hasAvailableOccupancy())
                .and(RoomSpecifications.hasDormitoryName(dormitoryName))
                .and(RoomSpecifications.hasMinCapacity(minCapacity))
                .and(RoomSpecifications.hasMaxCapacity(maxCapacity));

        // Lấy danh sách phòng phù hợp với Specification
        List<Room> rooms = roomRepository.findAll(spec).stream()
                .filter(r -> {
                    if (query == null || query.trim().isEmpty()) {
                        return true; // Nếu không có từ khóa, không lọc thêm
                    }
                    String lowerQuery = query.toLowerCase();
                    return r.getDormitory().getDormName().toLowerCase().contains(lowerQuery) ||
                            r.getRoomNumber().toLowerCase().contains(lowerQuery);
                })
                .collect(Collectors.toList());

        return rooms.stream()
                .map(room -> new RoomDTO(
                        room.getRoomId(),
                        room.getDormitory().getDormName(),
                        room.getRoomNumber(),
                        room.getCurrentOccupancy(),
                        room.getMaxCapacity()))
                .collect(Collectors.toList());
    }

    // DTO để trả về dữ liệu cho Select2
    public static class RoomDTO {
        private Long roomId;
        private String dormName;
        private String roomNumber;
        private int currentOccupancy;
        private int maxCapacity;

        public RoomDTO(Long roomId, String dormName, String roomNumber, int currentOccupancy, int maxCapacity) {
            this.roomId = roomId;
            this.dormName = dormName;
            this.roomNumber = roomNumber;
            this.currentOccupancy = currentOccupancy;
            this.maxCapacity = maxCapacity;
        }

        // Getters và Setters
        public Long getRoomId() { return roomId; }
        public void setRoomId(Long roomId) { this.roomId = roomId; }
        public String getDormName() { return dormName; }
        public void setDormName(String dormName) { this.dormName = dormName; }
        public String getRoomNumber() { return roomNumber; }
        public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
        public int getCurrentOccupancy() { return currentOccupancy; }
        public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
        public int getMaxCapacity() { return maxCapacity; }
        public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    }
}
