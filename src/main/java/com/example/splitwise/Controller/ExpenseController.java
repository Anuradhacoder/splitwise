package com.example.splitwise.Controller;

import com.example.splitwise.Service.ExpenseService;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PostMapping("/add")
    public UniversalResponse addExpense(@RequestBody ExpenseDto dto) {
        return service.addExpense(dto);
    }

    @GetMapping("/{userId}")
    public UniversalResponse getExpenses(@PathVariable(value = "userId") String userId) {
        return service.getALlExpenseDetailForAUserId(userId);
    }
}
