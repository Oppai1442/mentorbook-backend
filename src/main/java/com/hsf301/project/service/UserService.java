package com.hsf301.project.service;

import com.hsf301.project.exception.EmailConflictException;
import com.hsf301.project.exception.InvalidCredentials;
import com.hsf301.project.model.request.LoginRequest;
import com.hsf301.project.model.request.SignupRequest;
import com.hsf301.project.model.request.TokenRequest;
import com.hsf301.project.model.response.AuthResponse;
import com.hsf301.project.model.response.UserResponse;
import com.hsf301.project.model.user.User;
import com.hsf301.project.model.wallet.Wallet;
import com.hsf301.project.repository.UserRepository;
import com.hsf301.project.repository.WalletRepository;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ImageService imageService;

    public AuthResponse authenticate(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userRepository.findByEmail(email);
        
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidCredentials("Invalid username or password");
        }

        user.setLastActivity(LocalDateTime.now());
        userRepository.save(user);
        
        String token = jwtService.generateToken(user);
        UserResponse userResponse = new UserResponse(user, imageService);

        return new AuthResponse(token, userResponse);
    }

    public AuthResponse authenticate(TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        boolean isTokenValid = jwtService.validateToken(token);
        User user = null;

        if (isTokenValid) {
            Claims claims = jwtService.getClaims(token);
            String email = claims.getSubject();
            user = userRepository.findByEmail(email);
            if (user == null) {
                throw new InvalidCredentials("User not found");
            }
        } else {
            throw new InvalidCredentials("Invalid token");
        }

        user.setLastActivity(LocalDateTime.now());
        userRepository.save(user);
        
        String newToken = jwtService.generateToken(user);
        UserResponse userResponse = new UserResponse(user, imageService);

        return new AuthResponse(newToken, userResponse);
    }

    public AuthResponse register(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailConflictException("Email already registered");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setFullName(signupRequest.getFullName());
        user.setPhone(signupRequest.getPhoneNumber());

        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        walletRepository.save(wallet);

        String newToken = jwtService.generateToken(user);
        UserResponse userResponse = new UserResponse(user, imageService);

        return new AuthResponse(newToken, userResponse);
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public Page<User> findByFullNameContainingIgnoreCase(String filter, Pageable pageable) {
        return userRepository.findByFullNameContainingIgnoreCase(filter, pageable);
    }
}
