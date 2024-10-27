package com.hsf301.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Add this import

import com.hsf301.project.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
    boolean existsByEmail(String email);
    List<User> findByRole(String role);
    
    @Override
    Page<User> findAll(Pageable pageable);
    Page<User> findByRole(String role, Pageable pageable);

    // Phương thức tìm mentor theo tiêu chí lọc (ví dụ: theo tên)
    Page<User> findByFullNameContainingIgnoreCase(String filter, Pageable pageable);
}