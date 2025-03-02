package com.example.demo.controller;

import com.example.demo.dto.RoomFilter;
import com.example.demo.entity.Dormitory;
import com.example.demo.entity.Room;
import com.example.demo.entity.Room.RoomStatus;
import com.example.demo.entity.RoomType;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.DormitoryRepository;
import com.example.demo.repository.RoomTypeRepository;
import com.example.demo.specifications.RoomSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @GetMapping("/list")
    public String listRooms(
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String dormName,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Room.RoomStatus status,
            Model model
    ) {
        // Khởi tạo filter từ các tham số nhận được
        RoomFilter filter = new RoomFilter();
        filter.setRoomNumber(roomNumber);
        filter.setDormName(dormName);
        filter.setMaxCapacity(maxCapacity);
        filter.setGender(gender);
        filter.setPrice(price);
        filter.setStatus(status);

        // Tạo Specification từ filter
        Specification<Room> spec = RoomSpecification.filter(filter);

        // Lấy danh sách phòng với điều kiện lọc
        List<Room> rooms = roomRepository.findAll(spec);

        // Đưa vào model
        model.addAttribute("rooms", rooms);
        model.addAttribute("filter", filter);

        // Trả về trang list_rooms
        return "manager/room/list_rooms";
    }

    // Hiển thị form tạo phòng mới
    @GetMapping("/new")
    public String showCreateRoomForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("dormitories", dormitoryRepository.findAll());
        model.addAttribute("roomTypes", roomTypeRepository.findAll());
        return "manager/room/add_room";  // Đường dẫn đến template Thymeleaf, điều chỉnh cho phù hợp
    }

    // Xử lý lưu phòng mới
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute Room room,
                           @RequestParam("dormitoryId") Long dormId,
                           @RequestParam("roomTypeId") Long roomTypeId) {
        Dormitory dorm = dormitoryRepository.findById(dormId)
                .orElseThrow(() -> new RuntimeException("Dormitory not found"));
        RoomType type = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("RoomType not found"));

        // Gán dormitory, roomType, status và currentOccupancy mặc định
        room.setDormitory(dorm);
        room.setRoomType(type);
        room.setStatus(Room.RoomStatus.available);
        room.setCurrentOccupancy(0);

        roomRepository.save(room);
        return "redirect:/rooms/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable("id") Long id, Model model) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        model.addAttribute("room", room);
        model.addAttribute("dormitories", dormitoryRepository.findAll());
        model.addAttribute("roomTypes", roomTypeRepository.findAll());
        return "manager/room/edit_room";
    }

    @PostMapping("/update")
    public String updateRoom(@ModelAttribute Room room,
                             @RequestParam("dormitoryId") Long dormId,
                             @RequestParam("roomTypeId") Long roomTypeId) {
        // Tải thông tin phòng hiện tại từ DB
        Room existingRoom = roomRepository.findById(room.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Cập nhật các trường cần chỉnh sửa
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setDescription(room.getDescription());

        // Lấy thông tin Dormitory và RoomType từ repository
        Dormitory dorm = dormitoryRepository.findById(dormId)
                .orElseThrow(() -> new RuntimeException("Dormitory not found"));
        RoomType type = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("RoomType not found"));

        existingRoom.setDormitory(dorm);
        existingRoom.setRoomType(type);

        // Lưu cập nhật
        roomRepository.save(existingRoom);
        return "redirect:/rooms/list";
    }


}
