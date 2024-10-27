package com.hsf301.project.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.model.response.SkillResponse;
import com.hsf301.project.model.skills.Skills;
import com.hsf301.project.repository.SkillsRepository;

@Service
public class SkillsService {

    @Autowired
    private SkillsRepository skillCatalogueRepository;

    public List<SkillResponse> getAllSkill() {
        List<Skills> skillCatalogueList = skillCatalogueRepository.findAll();

        List<SkillResponse> skillResponses = skillCatalogueList.stream()
                .map(skillCatalogue -> new SkillResponse(
                        skillCatalogue.getSkillID(),
                        skillCatalogue.getSkillName(),
                        skillCatalogue.getSkillDescription()))
                .collect(Collectors.toList());

        return skillResponses;
    }

    public Map<Integer, Skills> getSkillsMap() {
        return skillCatalogueRepository.findAll().stream()
                .collect(Collectors.toMap(Skills::getSkillID, skill -> skill));
    }
}
