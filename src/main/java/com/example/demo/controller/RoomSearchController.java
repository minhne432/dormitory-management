//package com.example.demo.controller;
//
//import com.example.demo.dto.RoomDTO;
//import com.example.demo.entity.Room;
//import com.example.demo.repository.RoomRepository;
//import com.example.demo.service.RoomSearchService;
//import com.example.demo.specifications.RoomSpecifications;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/rooms")
//public class RoomSearchController {
//
//    private final RoomSearchService roomSearchService;
//
//    public RoomSearchController(RoomSearchService roomSearchService) {
//        this.roomSearchService = roomSearchService;
//    }
//
//    @GetMapping("/search")
//    public List<RoomDTO> searchRooms(
//            @RequestParam(value = "query", required = false) String query,
//            @RequestParam(value = "dormitoryName", required = false) String dormitoryName,
//            @RequestParam(value = "minCapacity", required = false) Integer minCapacity,
//            @RequestParam(value = "maxCapacity", required = false) Integer maxCapacity) {
//
//        return roomSearchService.searchRooms(query, dormitoryName, minCapacity, maxCapacity);
//    }
//}
//
