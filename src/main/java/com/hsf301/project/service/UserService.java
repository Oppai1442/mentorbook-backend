package com.hsf301.project.service;

import com.hsf301.project.model.User;
import com.hsf301.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Xác thực thành công
        }
        return null; // Xác thực thất bại
    }
}
