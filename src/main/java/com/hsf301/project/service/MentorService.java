package com.hsf301.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors; // Import cho Collectors

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsf301.project.model.dto.MentorAvailability;
import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.mentorData.MentorData;
import com.hsf301.project.model.request.MentorRequest;
import com.hsf301.project.model.response.MentorResponse;
import com.hsf301.project.model.response.MentorResponseList;
import com.hsf301.project.model.skills.Skills;
import com.hsf301.project.model.user.User;
import com.hsf301.project.utils.Utils;

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

    // public List<MentorResponse> getAllMentors() {
    // List<User> mentors = userService.findByRole("mentor");
    // List<MentorResponse> mentorResponses = new ArrayList<>();

    // Map<Integer, Skills> skillCatalogueMap = skillsService.getSkillsMap();

    // for (User mentor : mentors) {
    // MentorResponse response = new MentorResponse();

    // List<MentorBooking> totalBooked =
    // mentorBookingService.getAllBookingsByStatus(mentor, "done");
    // Double totalRating = 0.0;
    // for (MentorBooking booking : totalBooked) {
    // List<Feedback> feedback = feedbackService.findByBooking(booking);
    // for (Feedback feedbackItem : feedback) {
    // totalRating += feedbackItem.getRating();
    // }
    // }

    // // Bắt đầu:set skill
    // List<MentorData> mentorSkills = mentorDataService.getMentorById(mentor);
    // List<Skills> skills = new ArrayList<>();

    // for (MentorData mentorSkill : mentorSkills) {
    // List<Object> skillIds = new ArrayList<>();

    // try {
    // skillIds = utils.convertStringToJsonArray(mentorSkill.getMentorSkills());
    // } catch (Exception e) {
    // System.err.println("Error converting skills: " + e.getMessage());
    // }

    // for (Object skillId : skillIds) {
    // if (skillId instanceof Number) {
    // Integer id = ((Number) skillId).intValue();
    // Skills skillCatalogue = skillCatalogueMap.get(id);
    // if (skillCatalogue != null) {
    // skills.add(skillCatalogue);
    // } else {
    // System.out.println("Skill not found for ID: " + id);
    // }
    // } else {
    // System.out.println("Invalid skill ID type: " +
    // skillId.getClass().getSimpleName());
    // }
    // }
    // }

    // // Kết thúc: set skill

    // response.setUserId(mentor.getUserId());
    // response.setFullName(mentor.getFullName());
    // response.setEmail(mentor.getEmail());
    // response.setAvatarUrl(imageService.getImageUrl(mentor.getAvatar()));
    // response.setBackgroundUrl(imageService.getImageUrl(mentor.getBackground()));
    // response.setSkills(skills);
    // response.setTotalBooked(totalBooked.size());
    // if (!totalBooked.isEmpty()) {
    // response.setMentorRating(totalRating / totalBooked.size());
    // } else {
    // response.setMentorRating(0.0); // Nếu không có booking nào, có thể đặt rating
    // là 0
    // }

    // mentorResponses.add(response);
    // }

    // return mentorResponses;
    // }

    // public List<MentorResponse> getMentors(Integer page, String sort, String
    // filter) {
    public MentorResponseList getMentors(MentorRequest data) {
        // Lấy danh sách mentor
        List<User> mentors = userService.findByRole("mentor");
        List<MentorResponse> mentorResponses = new ArrayList<>();

        // Lấy danh sách kỹ năng từ skillsService
        Map<Integer, Skills> skillCatalogueMap = skillsService.getSkillsMap();
        ObjectMapper objectMapper = new ObjectMapper();

        for (User mentor : mentors) {
            MentorResponse response = new MentorResponse();

            // Lấy dữ liệu mentor
            MentorData mentorData = mentorDataService.getMentor(mentor);

            // Lấy danh sách booking đã hoàn tất
            List<MentorBooking> mentorTotalBooked = mentorBookingService.getAllBookingsByStatus(mentor, "done");

            // Lấy rating và các thông tin khác
            Double mentorRating = mentorData.getMentorRating();
            Double mentorPrice = mentorData.getMentorPrice();
            String mentorRole = mentorData.getMentorRole();
            String mentorSkillsData = mentorData.getMentorSkills();
            List<Object> skillIds = utils.convertStringToJsonArray(mentorSkillsData);
            List<Skills> mentorSkills = new ArrayList<>();

            // Xử lý từ skill id thành dữ liệu skill
            for (Object skillId : skillIds) {
                if (skillId instanceof Number) {
                    Integer id = ((Number) skillId).intValue();
                    Skills skillCatalogue = skillCatalogueMap.get(id);
                    if (skillCatalogue != null) {
                        mentorSkills.add(skillCatalogue);
                    } else {
                        System.out.println("Skill not found for ID: " + id);
                    }
                } else {
                    System.out.println("Invalid skill ID type: " + skillId.getClass().getSimpleName());
                }
            }

            // Xử lý dữ liệu availableTime từ JSON String
            String availableTimeJson = mentorData.getMentorAvailableTime();
            List<MentorAvailability> availableTimeList = new ArrayList<>();
            try {
                availableTimeList = objectMapper.readValue(
                        availableTimeJson,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MentorAvailability.class));
            } catch (JsonProcessingException e) {
                System.err.println("Failed to parse available time JSON: " + e.getMessage());
            }

            // Thiết lập thông tin cho MentorResponse
            response.setUserId(mentor.getUserId());
            response.setFullName(mentor.getFullName());
            response.setEmail(mentor.getEmail());
            response.setAvatarUrl(imageService.getImageUrl(mentor.getAvatar()));
            response.setBackgroundUrl(imageService.getImageUrl(mentor.getBackground()));
            response.setSkills(mentorSkills);
            response.setTotalBooked(mentorTotalBooked.size());
            response.setRating(mentorRating);
            response.setPrice(mentorPrice);
            response.setRole(mentorRole);
            response.setExperience(mentorData.getMentorExperience());
            response.setDescription(mentorData.getMentorDescription());
            response.setAvailableTime(availableTimeList);

            mentorResponses.add(response);
        }

        // Lọc kết quả dựa trên yêu cầu
        if (data.getSkillIds() != null && !data.getSkillIds().isEmpty()) {
            List<MentorResponse> t = new ArrayList<>();
            for (MentorResponse x : mentorResponses) {
                List<Skills> skills = x.getSkills();
                for (Integer skillId : data.getSkillIds()) {
                    if (skills.stream().anyMatch(skill -> skill.getSkillID() == (skillId))) {
                        t.add(x);
                        break;
                    }
                }
            }
            mentorResponses = t;
        }

        if (data.getRating() != null && !data.getRating().isEmpty()) {
            mentorResponses = mentorResponses.stream()
                    .filter(mentor -> {
                        Double rating = mentor.getRating();
                        // Điều kiện lọc rating
                        for (Integer rate : data.getRating()) {
                            if (rate == 5 && rating == 5)
                                return true;
                            if (rating >= rate && rating < (rate + 1))
                                return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }

        // Sắp xếp kết quả dựa trên yêu cầu
        if (data.getSorting() != null) {
            Comparator<MentorResponse> comparator = null;

            // Sắp xếp theo total booked
            if ("asc".equals(data.getSorting().getBookings())) {
                comparator = Comparator.comparing(MentorResponse::getTotalBooked);
            } else if ("desc".equals(data.getSorting().getBookings())) {
                comparator = Comparator.comparing(MentorResponse::getTotalBooked).reversed();
            }

            // Sắp xếp theo experience
            if ("asc".equals(data.getSorting().getExperience())) {
                comparator = Comparator.comparing(MentorResponse::getExperience);
            } else if ("desc".equals(data.getSorting().getExperience())) {
                comparator = Comparator.comparing(MentorResponse::getExperience).reversed();
            }

            // Sắp xếp theo rating
            if ("asc".equals(data.getSorting().getRating())) {
                comparator = Comparator.comparing(MentorResponse::getRating);
            } else if ("desc".equals(data.getSorting().getRating())) {
                comparator = Comparator.comparing(MentorResponse::getRating).reversed();
            }

            // Sắp xếp theo price
            if ("asc".equals(data.getSorting().getPrice())) {
                comparator = Comparator.comparing(MentorResponse::getPrice);
            } else if ("desc".equals(data.getSorting().getPrice())) {
                comparator = Comparator.comparing(MentorResponse::getPrice).reversed();
            }

            // Thực hiện sắp xếp nếu có comparator
            if (comparator != null) {
                mentorResponses.sort(comparator);
            }
        }

        // Tính tổng số mentor tìm thấy trước khi phân trang
        int totalFound = mentorResponses.size();

        // Chia trang
        int pageSize = 9;
        int fromIndex = Math.min(data.getPage() * pageSize, totalFound);
        int toIndex = Math.min(fromIndex + pageSize, totalFound);

        return new MentorResponseList(mentorResponses.subList(fromIndex, toIndex), totalFound);
    }

    // public MentorDetails getMentorData(String mentorId) {
    // User mentor = userService.findById(mentorId)
    // .orElseThrow(() -> new RuntimeException("Mentor not found"));

    // // Chuyển đổi Mentor sang MentorDetails (hoặc tạo từ trực tiếp từ mentor nếu
    // có)
    // return new MentorDetails(mentor);
    // }
}
