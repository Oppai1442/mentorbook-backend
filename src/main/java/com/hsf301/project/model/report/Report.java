package com.hsf301.project.model.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "Reports")
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "reporterId", referencedColumnName = "userId")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reportedUserId", referencedColumnName = "userId")
    private User reportedUser;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
