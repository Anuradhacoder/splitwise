package com.example.splitwise.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GroupDetail {
    private String groupName;
    private String groupId;
    private Date addedDate;
    private List<UserDto> users;
    private int totalAmountOwe;
    private int totalAmountOweToYou;
}
