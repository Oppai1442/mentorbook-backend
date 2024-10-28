package com.hsf301.project.service;

import com.hsf301.project.model.dto.Price;
import com.hsf301.project.model.dto.Sorting;
import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.mentorData.MentorData;
import com.hsf301.project.model.request.MentorRequest;
import com.hsf301.project.model.response.MentorResponse;
import com.hsf301.project.model.skills.Skills;
import com.hsf301.project.model.user.User;
import com.hsf301.project.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Import cho Collectors

@Service
public class MentorService {
    @Autowired
    private UserService userService;

    @Autowired
    private SkillsService skillsService;

    @Autowired
    private MentorDataService mentorDataService;

    @Autowired
    private MentorBookingService mentorBookingService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private Utils utils;

    public List<MentorResponse> getAllMentors() {
        List<User> mentors = userService.findByRole("mentor");
        List<MentorResponse> mentorResponses = new ArrayList<>();

        Map<Integer, Skills> skillCatalogueMap = skillsService.getSkillsMap();

        for (User mentor : mentors) {
            MentorResponse response = new MentorResponse();

            List<MentorBooking> totalBooked = mentorBookingService.getAllBookingsByStatus(mentor, "done");
            Double totalRating = 0.0;
            for (MentorBooking booking : totalBooked) {
                List<Feedback> feedback = feedbackService.findByBooking(booking);
                for (Feedback feedbackItem : feedback) {
                    totalRating += feedbackItem.getRating();
                }
            }

            // Bắt đầu:set skill
            List<MentorData> mentorSkills = mentorDataService.getMentorById(mentor);
            List<Skills> skills = new ArrayList<>();

            for (MentorData mentorSkill : mentorSkills) {
                List<Object> skillIds = new ArrayList<>();

                try {
                    skillIds = utils.convertStringToJsonArray(mentorSkill.getMentorSkills());
                } catch (Exception e) {
                    System.err.println("Error converting skills: " + e.getMessage());
                }

                for (Object skillId : skillIds) {
                    if (skillId instanceof Number) {
                        Integer id = ((Number) skillId).intValue();
                        Skills skillCatalogue = skillCatalogueMap.get(id);
                        if (skillCatalogue != null) {
                            skills.add(skillCatalogue);
                        } else {
                            System.out.println("Skill not found for ID: " + id);
                        }
                    } else {
                        System.out.println("Invalid skill ID type: " + skillId.getClass().getSimpleName());
                    }
                }
            }

            // Kết thúc: set skill

            response.setUserId(mentor.getUserId());
            response.setFullName(mentor.getFullName());
            response.setEmail(mentor.getEmail());
            response.setAvatarUrl(imageService.getImageUrl(mentor.getAvatar()));
            response.setBackgroundUrl(imageService.getImageUrl(mentor.getBackground()));
            response.setSkills(skills);
            response.setTotalBooked(totalBooked.size());
            if (!totalBooked.isEmpty()) {
                response.setMentorRating(totalRating / totalBooked.size());
            } else {
                response.setMentorRating(0.0); // Nếu không có booking nào, có thể đặt rating là 0
            }

            mentorResponses.add(response);
        }

        return mentorResponses;
    }

    // public List<MentorResponse> getMentors(Integer page, String sort, String
    // filter) {
    public List<MentorResponse> getMentors(MentorRequest data) {
        List<User> mentors = userService.findByRole("mentor");
        List<MentorResponse> mentorResponses = new ArrayList<>();

        Map<Integer, Skills> skillCatalogueMap = skillsService.getSkillsMap();

        for (User mentor : mentors) {
            MentorResponse response = new MentorResponse();

            List<MentorBooking> totalBooked = mentorBookingService.getAllBookingsByStatus(mentor, "done");
            Double totalRating = 0.0;
            for (MentorBooking booking : totalBooked) {
                List<Feedback> feedback = feedbackService.findByBooking(booking);
                for (Feedback feedbackItem : feedback) {
                    totalRating += feedbackItem.getRating();
                }
            }

            // Bắt đầu:set skill
            List<MentorData> mentorSkills = mentorDataService.getMentorById(mentor);
            List<Skills> skills = new ArrayList<>();

            for (MentorData mentorSkill : mentorSkills) {
                List<Object> skillIds = new ArrayList<>();

                try {
                    skillIds = utils.convertStringToJsonArray(mentorSkill.getMentorSkills());
                } catch (Exception e) {
                    System.err.println("Error converting skills: " + e.getMessage());
                }

                for (Object skillId : skillIds) {
                    if (skillId instanceof Number) {
                        Integer id = ((Number) skillId).intValue();
                        Skills skillCatalogue = skillCatalogueMap.get(id);
                        if (skillCatalogue != null) {
                            skills.add(skillCatalogue);
                        } else {
                            System.out.println("Skill not found for ID: " + id);
                        }
                    } else {
                        System.out.println("Invalid skill ID type: " + skillId.getClass().getSimpleName());
                    }
                }
            }

            // Kết thúc: set skill

            response.setUserId(mentor.getUserId());
            response.setFullName(mentor.getFullName());
            response.setEmail(mentor.getEmail());
            response.setAvatarUrl(imageService.getImageUrl(mentor.getAvatar()));
            response.setBackgroundUrl(imageService.getImageUrl(mentor.getBackground()));
            response.setSkills(skills);
            response.setTotalBooked(totalBooked.size());
            if (!totalBooked.isEmpty()) {
                response.setMentorRating(totalRating / totalBooked.size());
            } else {
                response.setMentorRating(0.0); // Nếu không có booking nào, có thể đặt rating là 0
            }

            mentorResponses.add(response);
        }

        return mentorResponses;
    }
}
