package com.millio.task.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Boolean read = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
