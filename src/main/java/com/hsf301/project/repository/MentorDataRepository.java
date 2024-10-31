package com.hsf301.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.mentorData.MentorData;

import java.util.List;
import com.hsf301.project.model.user.User;


@Repository
public interface MentorDataRepository extends JpaRepository<MentorData, Integer> {
    MentorData findByMentor(User mentor);
    
    // Tìm tất cả kỹ năng của một mentor theo mentorId
    List<MentorData> findByMentor_UserId(Integer mentorId);
    
    // Tìm kỹ năng theo ID của MentorData
    MentorData findBymentorDataId(Integer mentorDataId);
    
    // Tìm tất cả kỹ năng
    List<MentorData> findAll();
    
    // Xóa kỹ năng theo ID của MentorData
    void deleteBymentorDataId(Integer mentorDataId);
}