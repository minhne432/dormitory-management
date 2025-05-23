package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
@EqualsAndHashCode
public class Student {

    @Id
    @Column(name = "student_id")
    private Long studentId;  // trùng với User.userId

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "student_class", length = 50)
    private String studentClass;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    public enum AccountStatus {
        active, inactive
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentServiceRegistration> registrations;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ServiceUsage> serviceUsages; // Thêm danh sách sử dụng dịch vụ

    // Constructors, getters, setters...
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", fullName='" + fullName + '\'' +
                '}';
    }

}
