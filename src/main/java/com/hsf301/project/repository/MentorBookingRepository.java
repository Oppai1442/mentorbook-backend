package com.hsf301.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.user.User;

@Repository
public interface MentorBookingRepository extends JpaRepository<MentorBooking, Integer> {
    List<MentorBooking> findByMentor_UserIdAndStatus(Integer mentorId, String status);

    List<MentorBooking> findByCustomerAndStatus(User user, String status);

    MentorBooking findByBookingId(Long bookingId);

    List<MentorBooking> findByMentor_UserId(Integer mentorId);

    List<MentorBooking> findByMentor(User mentor);
    List<MentorBooking> findByCustomer(User customer);

    @Query(value = "SELECT * FROM MentorBookings mb WHERE mb.mentor_id = :mentorId AND MONTH(mb.booking_date) = :month AND YEAR(mb.booking_date) = :year AND mb.status = 'accepted'", nativeQuery = true)
    List<MentorBooking> findBookingsByMentorAndMonth(@Param("mentorId") Long mentorId, @Param("month") int month,
            @Param("year") int year);

    @Query(value = "SELECT * FROM MentorBookings mb WHERE mb.mentor_id = :mentorId AND mb.booking_date BETWEEN :startOfWeek AND :endOfWeek AND mb.status = 'accepted'", nativeQuery = true)
    List<MentorBooking> findBookingsByMentorAndWeek(@Param("mentorId") Long mentorId,
            @Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query(value = "SELECT * FROM MentorBookings mb WHERE mb.mentor_id = :mentorId AND CAST(mb.booking_date AS DATE) = :date AND mb.status = 'accepted'", nativeQuery = true)
    List<MentorBooking> findBookingsByMentorAndDate(@Param("mentorId") Long mentorId, @Param("date") LocalDate date);
}