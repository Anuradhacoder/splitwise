package com.example.splitwise.Model;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserGroup")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "userId")
    private String userId;
    @Column(name ="groupID")
    private  String groupId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "deleted")
    private boolean deleted;
}
