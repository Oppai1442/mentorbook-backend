package com.hsf301.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsf301.project.model.request.MentorRequest;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.MentorResponse;
import com.hsf301.project.model.response.MentorResponseList;
import com.hsf301.project.service.MentorService;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping("/get-mentors")
    public ResponseEntity<ApiResponse<MentorResponseList>> getMentors(@RequestBody MentorRequest data) {
        MentorResponseList mentors = mentorService.getMentors(data);
        
        return ResponseEntity.ok(new ApiResponse<MentorResponseList>(mentors));
    }
}