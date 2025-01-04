package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignRoomRequest {
    private List<Long> applicationIds;
    private Long roomId;
    // Constructors ...
}
