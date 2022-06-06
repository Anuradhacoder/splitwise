package com.example.splitwise.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
    @Column(name = "userName")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "userId")
    private String userId;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "password")
    private String password;
}
