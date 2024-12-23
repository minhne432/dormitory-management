package com.example.demo.repository;

import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatusAndCurrentOccupancyLessThan(Room.RoomStatus status, int max);
    // Lọc các phòng đang 'available' và occupancy < max_capacity
}