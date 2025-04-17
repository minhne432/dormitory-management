package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NEWS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private String author;                // tuỳ ý

    private LocalDateTime createdAt;      // thời điểm đăng

    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    void preUpdate() { this.updatedAt = LocalDateTime.now(); }
}
