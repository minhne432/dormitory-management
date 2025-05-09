package com.example.demo.repository;

import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {


    /**
     * Lọc các phòng đang 'AVAILABLE' và occupancy < max_capacity
     */
    List<Room> findByStatusAndCurrentOccupancyLessThan(Room.RoomStatus status, int max);

    /**
     * Tìm các tên khu ký túc xá duy nhất
     */
    @Query("SELECT DISTINCT r.dormitory.dormName FROM Room r")
    List<String> findDistinctDormitoryNames();

    List<Room> findByDormitoryDormIdAndCurrentOccupancyGreaterThan(Long dormId, int currentOccupancy);


}
