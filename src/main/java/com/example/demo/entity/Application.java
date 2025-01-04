package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "applications") // Chữ thường hoặc hoa đều được, miễn khớp với tên bảng
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    /**
     * student_id trong DB là kiểu bigint(20).
     * Ta sẽ liên kết @ManyToOne với Student (bên dưới) thông qua khoá ngoại student_id.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;
    // DB có enum('approved','pending','rejected','completed')
    // EnumType.STRING sẽ lưu giá trị chuỗi tương ứng

    /**
     * approved_by trong DB là bigint(20).
     * Nếu chưa có bảng MANAGER, hoặc ta chỉ lưu ID của người phê duyệt,
     * thì nên map thẳng sang kiểu Long thay vì kiểu Manager.
     */
    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Manager approvedBy;  // có thể null

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "dormitory_area")
    private String dormitoryArea;

    @Column(name = "note", length = 255)
    private String note;

    /**
     * Enum mô tả trạng thái (phải tương ứng với các giá trị trong enum của DB).
     */
    public enum ApplicationStatus {
        approved,
        pending,
        rejected,
        completed
    }
}
