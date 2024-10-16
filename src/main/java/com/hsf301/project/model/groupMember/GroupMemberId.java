package com.hsf301.project.model.groupMember;

import java.io.Serializable;
import lombok.Data;

@Data
public class GroupMemberId implements Serializable {
    private Integer group;
    private Integer user;
}
