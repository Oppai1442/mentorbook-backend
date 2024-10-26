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
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private ProjectGroup group;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "role_in_group", length = 50)
    private String roleInGroup;

    @Column(name = "joined_date", nullable = false)
    private LocalDateTime joinedDate = LocalDateTime.now();
}
