package com.hsf301.project.model.adminKey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "AdminKeys")
@NoArgsConstructor
@AllArgsConstructor
public class AdminKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keyId;

    @Column(nullable = false, unique = true, length = 100)
    private String keyValue;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
