package com.hsf301.project.service;

import com.hsf301.project.exception.EmailConflictException;
import com.hsf301.project.exception.InvalidCredentials;
import com.hsf301.project.model.user.SignupRequest;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.UserRepository;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidCredentials("Invalid username or password");
        }

        return user;
    }

    public User authenticated(String token) {
        boolean isTokenValid = jwtService.validateToken(token);
        User user = null;

        if (isTokenValid) {
            Claims claims = jwtService.getClaims(token);
            String email = claims.getSubject();
            user = userRepository.findByEmail(email);
        }

        return user;
    }

    public User register(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailConflictException("Email already registered");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setFullName(signupRequest.getFullName());
        user.setPhone(signupRequest.getPhone());
        user.setRole("user");
        user.setCreatedDate(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }
}
