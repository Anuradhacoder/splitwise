package com.example.splitwise.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "groupTable")
public class GroupTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "groupId")
    private String groupId;
    @Column(name="addedDate")
    private Date addedDate;
    @Column(name="deleted")
    private Boolean deleted;
    @Column(name = "createdBy")
    private String createdBy;
}
