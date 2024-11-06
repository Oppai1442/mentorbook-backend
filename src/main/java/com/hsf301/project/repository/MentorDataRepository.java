package com.hsf301.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.mentorData.MentorData;

import java.util.List;
import com.hsf301.project.model.user.User;


@Repository
public interface MentorDataRepository extends JpaRepository<MentorData, Integer> {
    MentorData findByMentor(User mentor);
    
    List<MentorData> findByMentor_UserId(Integer mentorId);
    
    MentorData findBymentorDataId(Integer mentorDataId);
    
    List<MentorData> findAll();
    
    void deleteBymentorDataId(Integer mentorDataId);
}