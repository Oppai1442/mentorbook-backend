package com.hsf301.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.skillCatalogue.SkillCatalogue;

@Repository
public interface SkillCatalogueRepository extends JpaRepository<SkillCatalogue, Integer> {
    SkillCatalogue findBySkillID(int SkillID);
    List<SkillCatalogue> findAll();
}