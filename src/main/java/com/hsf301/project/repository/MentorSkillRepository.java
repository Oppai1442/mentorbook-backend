package com.hsf301.project.repository;

import com.hsf301.project.model.mentorSkill.MentorSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorSkillRepository extends JpaRepository<MentorSkill, Integer> {
    
    // Tìm tất cả kỹ năng của một mentor theo mentorId
    List<MentorSkill> findByMentor_UserId(Integer mentorId);
    
    // Tìm kỹ năng theo ID của MentorSkill
    MentorSkill findByMentorSkillId(Integer mentorSkillId);
    
    // Tìm tất cả kỹ năng
    List<MentorSkill> findAll();
    
    // Xóa kỹ năng theo ID của MentorSkill
    void deleteByMentorSkillId(Integer mentorSkillId);
}