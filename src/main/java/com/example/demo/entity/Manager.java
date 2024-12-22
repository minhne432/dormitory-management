package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MANAGERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {

    @Id
    @Column(name = "manager_id")
    private Long managerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "manager_id")
    private User user;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    // Constructors, getters, setters...
}
