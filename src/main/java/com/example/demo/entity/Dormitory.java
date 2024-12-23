package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DORMITORIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dorm_id")
    private Long dormId;

    @Column(name = "dorm_name", nullable = false, length = 100)
    private String dormName;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "description", length = 255)
    private String description;

    // Nếu muốn ánh xạ 1-n với Rooms:
    // @OneToMany(mappedBy = "dormitory", cascade = CascadeType.ALL)
    // private List<Room> rooms;

    // Constructors, getters, setters...
}
