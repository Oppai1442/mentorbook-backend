package com.hsf301.project.model.groupMember;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.projectGroup.ProjectGroup;
import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "GroupMembers")
@IdClass(GroupMemberId.class) // Composite key class
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @Id
    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId")
    private ProjectGroup group;

    @Id
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(length = 50)
    private String roleInGroup;

    @Column(nullable = false)
    private LocalDateTime joinedDate = LocalDateTime.now();
}
