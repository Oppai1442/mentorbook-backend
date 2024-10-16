package com.hsf301.project.model.projectGroup;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "ProjectGroups")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @Column(nullable = false, length = 100)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
