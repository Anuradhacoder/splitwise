package com.example.splitwise.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
    @Column(name = "expenseId")
    private String expenseId;
    @Column(name = "groupId")
    private String groupId;
    @Column(name = "paidBy")
    private String paidBy;
    @Column(name = "addedDate")
    private Date addedDate;
    @Column(name = "settled")
    private boolean settled;
    @Column(name = "totalAmount")
    private int totalAmount;
    @Column(name = "users")
    private String users;
}
