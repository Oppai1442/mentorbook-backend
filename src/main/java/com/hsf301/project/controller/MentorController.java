package com.hsf301.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.MentorResponse;
import com.hsf301.project.service.MentorService;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @GetMapping("/get-all-mentors")
    public ResponseEntity getMethodName() {
        List<MentorResponse> mentors = mentorService.getAllMentors();
        return ResponseEntity.ok(new ApiResponse(mentors));
    }

}