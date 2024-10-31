package com.hsf301.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.model.mentorData.MentorData;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.MentorDataRepository;

@Service
public class MentorDataService {
    @Autowired
    private MentorDataRepository mentorDataRepository;

    public MentorData getMentor(User mentor) {
        return mentorDataRepository.findByMentor(mentor);
    }
}
