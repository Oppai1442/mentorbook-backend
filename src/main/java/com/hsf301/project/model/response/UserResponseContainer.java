package com.hsf301.project.model.response;

import java.util.List;
import com.hsf301.project.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseContainer {
    private List<User> users;
    private int totalFound;
}
