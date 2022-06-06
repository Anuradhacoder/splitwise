package com.example.splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseDetail {
    private int totalAmountOwe;
    private int totalAmountOweToYou;
}
