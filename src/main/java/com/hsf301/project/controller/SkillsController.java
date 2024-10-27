package com.hsf301.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.SkillResponse;
import com.hsf301.project.service.SkillsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/skill")
public class SkillsController {

    @Autowired
    SkillsService skillCatalogueService;

    @GetMapping("/get-all-skills")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkill() {
        List<SkillResponse> skillResponse = skillCatalogueService.getAllSkill();
        
        return ResponseEntity.ok(new ApiResponse<List<SkillResponse>>(skillResponse));
    }

}
