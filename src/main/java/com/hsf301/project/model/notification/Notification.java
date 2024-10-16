package com.hsf301.project.model.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "Notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private UUID notificationId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(length = 255)
    private String message;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
