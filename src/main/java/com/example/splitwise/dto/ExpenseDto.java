package com.example.splitwise.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseDto {
    private String loggedInUser;
    private String expenseName;
    private int amount;
    private String paidBy;
    private List<String> userIds;
    private String groupId;
}
