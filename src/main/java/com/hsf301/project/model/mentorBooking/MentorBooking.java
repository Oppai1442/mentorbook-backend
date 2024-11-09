package com.hsf301.project.model.mentorBooking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "MentorBookings")
@NoArgsConstructor
@AllArgsConstructor
public class MentorBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "user_id")
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private User customer;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "status", nullable = false, length = 10)
    private String status = "pending";
    // pending là khi vừa tạo
    // done là khi buổi booking đã kết thúc
    // reject là khi mentor từ chối
    // accept là khi mentor ok

    @Column(name = "meet_url", length = 50)
    private String meetUrl;

    @Column(name = "duration", nullable = false, columnDefinition = "decimal(5,2) DEFAULT 0.0")
    private double duration = 0.0;

    @Column(name = "extend_duration", nullable = false, columnDefinition = "decimal(5,2) DEFAULT 0.0")
    private double extendDuration = 0.0;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;
}
