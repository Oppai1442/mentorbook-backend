package com.hsf301.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.skills.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    Skills findBySkillID(int SkillID);
    List<Skills> findAll();
}