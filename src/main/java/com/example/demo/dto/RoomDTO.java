package com.example.demo.dto;

public class RoomDTO {
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
