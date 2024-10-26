package com.hsf301.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Add this import

import com.hsf301.project.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
    boolean existsByEmail(String email);
    List<User> findByRole(String role);
}