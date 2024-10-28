package com.hsf301.project.model.mentorBooking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    @Column(name="booking_date", nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(name="status", nullable = false, length = 20)
    private String status;
}
