package com.example.demo;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Student;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.BillService;
import com.example.demo.service.impl.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillServiceImpl billService; // Dùng implementation thay vì interface

    private Student student;
    private Bill bill1;
    private Bill bill2;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);

        bill1 = Bill.builder()
                .billId(101L)
                .billingPeriod("2025-02")
                .issueDate(LocalDate.now().minusDays(10))
                .dueDate(LocalDate.now().plusDays(5))
                .status(Bill.BillStatus.unpaid)
                .totalAmount(500.0)
                .student(student)
                .build();

        bill2 = Bill.builder()
                .billId(102L)
                .billingPeriod("2025-03")
                .issueDate(LocalDate.now().minusDays(15))
                .dueDate(LocalDate.now().plusDays(10))
                .status(Bill.BillStatus.paid)
                .totalAmount(700.0)
                .student(student)
                .build();
    }

    @Test
    void getBillsByStudentId_ShouldReturnBills_WhenStudentExists() {
        // Giả lập Student tồn tại trong DB
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Giả lập danh sách hóa đơn từ billRepository
        when(billRepository.findByStudentStudentId(1L)).thenReturn(Arrays.asList(bill1, bill2));

        // Gọi phương thức cần test
        List<Bill> result = billService.getBillsByStudentId(1L);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(2, result.size());

        // Kiểm tra chi tiết hóa đơn
        assertEquals(101L, result.get(0).getBillId());
        assertEquals("2025-02", result.get(0).getBillingPeriod());
        assertEquals(Bill.BillStatus.unpaid, result.get(0).getStatus());
        assertEquals(500.0, result.get(0).getTotalAmount());

        assertEquals(102L, result.get(1).getBillId());
        assertEquals("2025-03", result.get(1).getBillingPeriod());
        assertEquals(Bill.BillStatus.paid, result.get(1).getStatus());
        assertEquals(700.0, result.get(1).getTotalAmount());

        // Kiểm tra phương thức repository có được gọi hay không
        verify(studentRepository, times(1)).findById(1L);
        verify(billRepository, times(1)).findByStudentStudentId(1L);
    }

    @Test
    void getBillsByStudentId_ShouldThrowException_WhenStudentNotFound() {
        // Giả lập trường hợp student không tồn tại
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        // Kiểm tra nếu gọi phương thức với student không tồn tại, thì nó sẽ ném lỗi IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> billService.getBillsByStudentId(2L));

        assertEquals("Không tìm thấy sinh viên với ID: 2", exception.getMessage());

        // Kiểm tra phương thức repository có được gọi đúng không
        verify(studentRepository, times(1)).findById(2L);
        verify(billRepository, never()).findByStudentStudentId(anyLong());
    }
}
