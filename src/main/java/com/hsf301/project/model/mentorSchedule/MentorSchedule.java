package com.hsf301.project.model.mentorSchedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "MentorSchedules")
@NoArgsConstructor
@AllArgsConstructor
public class MentorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @ManyToOne
    @JoinColumn(name = "mentorId", referencedColumnName = "userId")
    private User mentor;

    @Column(nullable = false)
    private LocalDateTime availableFrom;

    @Column(nullable = false)
    private LocalDateTime availableTo;
}
