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
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "mentorId", referencedColumnName = "userId")
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "userId")
    private User student;

    @Column(nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String status;
}
