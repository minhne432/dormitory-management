package com.example.demo.service;

import com.example.demo.dto.BillFilterRequest;
import com.example.demo.entity.Bill;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BillService {
    Bill createRoomBill(Long studentId);
    boolean billExistsForStudent(Long studentId, LocalDate monthStart);
    void createRoomBillForAll();

    public Bill createBillForRegistrations(List<Long> registrationIds);

    public List<Bill> getBillsByStudentId(Long studentId);

    public Page<Bill> getBillsByFilter(BillFilterRequest filterRequest);
}
