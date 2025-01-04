package com.example.demo.controller;

import com.example.demo.dto.AssignRoomRequest;
import com.example.demo.exception.NotEnoughCapacityException;
import com.example.demo.service.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/assign-room")
public class RoomAssignmentController {

    @Autowired
    private RoomAssignmentService roomAssignmentService;

    @PostMapping
    public ResponseEntity<?> assignRoom(@RequestBody AssignRoomRequest request) {
        try {
            roomAssignmentService.assignRoomToApplications(
                    request.getRoomId(),
                    request.getApplicationIds()
            );
            // Trả về JSON thông báo success
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("message", "Xếp phòng thành công!");
            return ResponseEntity.ok(resp);

        } catch (NotEnoughCapacityException e) {
            // Custom exception khi phòng không đủ chỗ
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", e.getMessage());
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            // Lỗi khác
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "Có lỗi xảy ra: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

}
