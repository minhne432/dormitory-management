package com.example.demo.service;


import com.example.demo.dto.RoomDTO;
import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.specifications.RoomSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomSearchService {

    private final RoomRepository roomRepository;

    public RoomSearchService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDTO> searchRooms(String query, String dormitoryName, Integer minCapacity, Integer maxCapacity) {
        Specification<Room> spec = Specification.where(RoomSpecifications.hasAvailableStatus())
                .and(RoomSpecifications.hasAvailableOccupancy())
                .and(RoomSpecifications.hasDormitoryName(dormitoryName))
                .and(RoomSpecifications.hasMinCapacity(minCapacity))
                .and(RoomSpecifications.hasMaxCapacity(maxCapacity));

        List<Room> rooms = roomRepository.findAll(spec).stream()
                .filter(r -> {
                    if (query == null || query.trim().isEmpty()) {
                        return true;
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
}
