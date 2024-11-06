package com.hsf301.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.user.User;


@Repository
public interface MentorBookingRepository extends JpaRepository<MentorBooking, Integer> {
    List<MentorBooking> findByMentor_UserIdAndStatus(Integer mentorId, String status);
    List<MentorBooking> findByUserAndStatus(User user, String status);
    MentorBooking findByBookingId(Long bookingId);
    List<MentorBooking> findByMentor_UserId(Integer mentorId);
}