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
    @Column(name = "report_id")
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reported_user_id", referencedColumnName = "user_id")
    private User reportedUser;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
