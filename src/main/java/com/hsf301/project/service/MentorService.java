package com.hsf301.project.service;

import com.hsf301.project.model.mentorSkill.MentorSkill;
import com.hsf301.project.model.response.MentorResponse;
import com.hsf301.project.model.skillCatalogue.SkillCatalogue;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.MentorSkillRepository;
import com.hsf301.project.repository.SkillCatalogueRepository;
import com.hsf301.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MentorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillCatalogueRepository skillCatalogueRepository;

    @Autowired
    private MentorSkillRepository mentorSkillRepository;

   
    public List<MentorResponse> getAllMentors() {
        // Lấy danh sách mentor từ repository
        List<User> mentors = userRepository.findByRole("mentor");
    
        // Tạo danh sách để chứa phản hồi
        List<MentorResponse> mentorResponses = new ArrayList<>();
    
        // Lấy tất cả kỹ năng từ SkillCatalogue vào một bản đồ để dễ dàng truy cập
        Map<Integer, SkillCatalogue> skillCatalogueMap = skillCatalogueRepository.findAll().stream()
                .collect(Collectors.toMap(
                        SkillCatalogue::getSkillID, // Khóa là ID kỹ năng
                        skill -> skill, // Giá trị là đối tượng SkillCatalogue
                        (existing, replacement) -> existing // Nếu có trùng lặp, giữ lại giá trị cũ
                ));
    
        // Duyệt qua từng mentor
        for (User mentor : mentors) {
            MentorResponse response = new MentorResponse();
            response.setUserId(mentor.getUserId());
            response.setFullName(mentor.getFullName());
            response.setEmail(mentor.getEmail());
    
            // Lấy tất cả kỹ năng của mentor từ mentorSkillRepository
            List<MentorSkill> mentorSkills = mentorSkillRepository.findByMentor_UserId(mentor.getUserId());
            
            // Lấy danh sách kỹ năng từ mentorSkills và ánh xạ đến SkillCatalogue
            List<SkillCatalogue> skills = new ArrayList<>();
            for (MentorSkill mentorSkill : mentorSkills) {
                // Giả sử mentorSkill.getSkills() trả về danh sách ID kỹ năng
                List<String> skillIds = Arrays.asList(mentorSkill.getSkills()); // Chuyển đổi nếu cần
    
                for (String skillId : skillIds) {
                    SkillCatalogue skillCatalogue = skillCatalogueMap.get(skillId); // Tìm theo ID
                    if (skillCatalogue != null) {
                        skills.add(skillCatalogue); // Thêm kỹ năng vào danh sách
                    }
                }
            }
    
            response.setSkills(skills); // Gán danh sách kỹ năng cho phản hồi
            mentorResponses.add(response); // Thêm phản hồi vào danh sách
        }
    
        return mentorResponses; // Trả về danh sách MentorResponse
    }
}
