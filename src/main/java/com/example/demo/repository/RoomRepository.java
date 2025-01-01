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

//    /**
//     * Tìm phòng khả dụng dựa trên các tiêu chí lọc
//     */
//    @Query("SELECT r FROM Room r WHERE " +
//            "(:dormitoryName IS NULL OR r.dormitory.dormName = :dormitoryName) AND " +
//            "(:minCapacity IS NULL OR r.maxCapacity >= :minCapacity) AND " +
//            "(:maxCapacity IS NULL OR r.maxCapacity <= :maxCapacity) AND " +
//            "r.currentOccupancy < r.maxCapacity AND " +
//            "r.status = :status")
//    List<Room> findAvailableRooms(
//            @Param("dormitoryName") String dormitoryName,
//            @Param("minCapacity") Integer minCapacity,
//            @Param("maxCapacity") Integer maxCapacity,
//            @Param("status") Room.RoomStatus status
//    );
//
//    /**
//     * Overload phương thức để sử dụng mặc định RoomStatus.AVAILABLE
//     */
//    default List<Room> findAvailableRooms(String dormitoryName, Integer minCapacity, Integer maxCapacity) {
//        return findAvailableRooms(dormitoryName, minCapacity, maxCapacity, Room.RoomStatus.available);
//    }
}
