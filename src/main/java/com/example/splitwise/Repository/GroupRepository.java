package com.example.splitwise.Repository;

import com.example.splitwise.Model.GroupTable;
import com.example.splitwise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupTable, Long> {
    Optional<GroupTable> findByGroupNameAndDeleted(String groupName, boolean deleted);

    Optional<GroupTable> findByGroupName(String groupName);

    Optional<GroupTable> findByGroupId(String groupId);



}

