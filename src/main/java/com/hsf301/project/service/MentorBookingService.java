package com.hsf301.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.MentorBookingRepository;


@Service
public class MentorBookingService {
    @Autowired
    private MentorBookingRepository mentorBookingRepository;

    public List<MentorBooking> getAllBookingsByStatus(User mentor, String status) {
        return mentorBookingRepository.findByUserAndStatus(mentor, status);
    }
    
}
