package com.example.splitwise.Service;

import com.example.splitwise.Model.Expense;
import com.example.splitwise.Model.GroupTable;
import com.example.splitwise.Model.User;
import com.example.splitwise.Model.UserGroup;
import com.example.splitwise.Repository.ExpenseRepository;
import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserGroupRepository;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.ExpenseDetail;
import com.example.splitwise.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public UniversalResponse addExpense(ExpenseDto dto) {
        UniversalResponse response = new UniversalResponse();
        Optional<GroupTable> optionalGroupTable = groupRepository.findByGroupId(dto.getGroupId());
        System.out.println(dto);
        System.out.println(optionalGroupTable);
        if (!optionalGroupTable.isPresent()) {
            ResponseCodeJson codeJson = new ResponseCodeJson();
            codeJson.setCode(404);
            codeJson.setMessage("Group not found");
            response.setResponseCodeJson(codeJson);
            return response;
        }
        List<String> users = userGroupRepository.getALlUsersForAGroup(dto.getGroupId());
        Expense expense = new Expense();
        expense.setExpenseId(UUID.randomUUID().toString());
        expense.setAddedDate(new Date());
        expense.setGroupId(dto.getGroupId());
        expense.setSettled(false);
        expense.setPaidBy(dto.getPaidBy());
        expense.setUsers(convertToCommaSeparated(users));
        expense.setTotalAmount(dto.getAmount());
        repository.save(expense);
        ExpenseDetail expenseDetail = getGroupExpenseDetailForAUserId(dto.getLoggedInUser(), dto.getGroupId());
        ResponseCodeJson codeJson = new ResponseCodeJson();
        codeJson.setCode(200);
        codeJson.setMessage("Added expense successfully");
        response.setObject(expenseDetail);
        return response;
    }

    public ExpenseDetail getGroupExpenseDetailForAUserId(String userId, String groupId) {
        int totalAmountOwe = 0;
        int totalAmountOweToYou = 0;
        List<Expense> expenseList = repository.findByGroupId(groupId);
        for (Expense expense : expenseList) {
            List<String> s = convertToList(expense.getUsers());
            if (s.contains(userId)) {
                int total = expense.getTotalAmount();
                int share = total / s.size();
                if (expense.getPaidBy().equals(userId)) {
                    totalAmountOweToYou += (total - share);
                } else {
                    totalAmountOwe += share;
                }
            }
        }
        return new ExpenseDetail(totalAmountOwe, totalAmountOweToYou);
    }

    public UniversalResponse getALlExpenseDetailForAUserId(String userId) {
        List<String> groupIds = userGroupRepository.getDistinctGroupIds(userId);
        int totalAmountOwe = 0;
        int totalAmountOweToYou = 0;
        for (String id : groupIds) {
            ExpenseDetail expenseDetail = getGroupExpenseDetailForAUserId(userId, id);
            totalAmountOwe += expenseDetail.getTotalAmountOwe();
            totalAmountOweToYou += expenseDetail.getTotalAmountOweToYou();
        }
        UniversalResponse response = new UniversalResponse();
        response.setObject(new ExpenseDetail(totalAmountOwe, totalAmountOweToYou));
        return response;
    }

    public String convertToCommaSeparated(List<String> users) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < users.size(); i++) {
            sb.append(users.get(i));
            if(i < users.size() - 1) {
                sb.append(",");
            }

        }
        return sb.toString();
    }

    public List<String> convertToList(String s) {
        return Arrays.asList(s.split(","));
    }
}
