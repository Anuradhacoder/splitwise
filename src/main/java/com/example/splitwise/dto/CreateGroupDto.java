package com.example.splitwise.dto;

import com.example.splitwise.Model.User;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupDto {
    private String groupName;
    private List<String> userIds;
}
