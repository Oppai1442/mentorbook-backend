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
    @Column(name = "key_id")
    private Integer keyId;

    @Column(name="key_value", nullable = false, unique = true, length = 100)
    private String keyValue;

    @Column(name="isused", nullable = false)
    private Boolean isUsed = false;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
