package com.example.demo.service;

import com.example.demo.entity.Bill;

import java.time.LocalDate;

public interface BillService {
    Bill createRoomBill(Long studentId);
    boolean billExistsForStudent(Long studentId, LocalDate monthStart);
    void createRoomBillForAll();
}
