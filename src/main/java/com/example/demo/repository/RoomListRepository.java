package com.example.demo.repository;

import com.example.demo.entity.RoomList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomListRepository extends JpaRepository<RoomList, Long> {
}
