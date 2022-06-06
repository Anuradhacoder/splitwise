package com.example.splitwise.Repository;

import com.example.splitwise.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    Optional<UserGroup> findByUserIdAndGroupId(String userId,String groupId);

    @Query("Select distinct groupId from UserGroup where userId =:userId and deleted = false")
    List<String> getDistinctGroupIds(@Param("userId") String userId);

    @Query("Select distinct userId from UserGroup where groupId =:groupId and deleted = false")
    List<String> getALlUsersForAGroup(@Param("groupId") String groupId);

    List<UserGroup> findAllByGroupId(String groupId);
}
