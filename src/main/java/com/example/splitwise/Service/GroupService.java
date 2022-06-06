package com.example.splitwise.Service;

import com.example.splitwise.Model.GroupTable;
import com.example.splitwise.Model.User;
import com.example.splitwise.Model.UserGroup;
import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserGroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.CreateGroupDto;
import com.example.splitwise.dto.ExpenseDetail;
import com.example.splitwise.dto.GroupDetail;
import com.example.splitwise.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.*;
import java.util.*;

@Service
public class GroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseService expenseService;

    public ResponseCodeJson checkGroup(String groupName) {
        ResponseCodeJson codeJson = new ResponseCodeJson();
        Optional<GroupTable> optionalGroupName = groupRepository.findByGroupNameAndDeleted(groupName,false);
        if (optionalGroupName.isPresent()) {
            codeJson.setCode(401);
            codeJson.setMessage("group already present");
            return codeJson;
        }
        codeJson.setCode(200);
        codeJson.setMessage("group not present");
        return codeJson;
    }

    public UniversalResponse createGroup(CreateGroupDto dto) {
        UniversalResponse response = new UniversalResponse();
        Optional<GroupTable> optionalGroupName = groupRepository.findByGroupNameAndDeleted(dto.getGroupName(),false);
        if (optionalGroupName.isPresent()) {
            ResponseCodeJson responseCodeJson = new ResponseCodeJson();
            responseCodeJson.setCode(401);
            responseCodeJson.setMessage("group already present");
            response.setResponseCodeJson(responseCodeJson);
            return response;
        }
        GroupTable groupTable=new GroupTable();
        groupTable.setGroupName(dto.getGroupName());
        groupTable.setGroupId(UUID.randomUUID().toString());
        groupTable.setDeleted(false);
        groupTable.setAddedDate(new Date());
        groupRepository.save(groupTable);
        List<UserDto> users = new ArrayList<>();
        for (String user : dto.getUserIds()) {
            Optional<User> optionalUser = userRepository.findByEmail(user);
            System.out.println(optionalUser);
            if (optionalUser.isPresent()) {
                User u = optionalUser.get();
                UserGroup userGroup = new UserGroup();
                userGroup.setUserName(u.getUserName());
                userGroup.setUserId(u.getUserId());
                userGroup.setGroupId(groupTable.getGroupId());
                userGroup.setGroupName(dto.getGroupName());
                userGroup.setDeleted(false);
                UserDto userDto = new UserDto();
                userDto.setUserId(u.getUserId());
                userDto.setUserName(u.getUserName());
                userDto.setEmailId(u.getEmail());
                users.add(userDto);
                userGroupRepository.save(userGroup);
            }
        }
        GroupDetail groupDetail = new GroupDetail();
        groupDetail.setGroupId(groupTable.getGroupId());
        groupDetail.setGroupName(groupTable.getGroupName());
        groupDetail.setAddedDate(groupTable.getAddedDate());
        groupDetail.setUsers(users);

        ResponseCodeJson responseCodeJson = new ResponseCodeJson();
        responseCodeJson.setCode(200);
        responseCodeJson.setMessage("group created successfully");
        response.setObject(groupDetail);
        response.setResponseCodeJson(responseCodeJson);
        return response;
    }

    public UniversalResponse getGroups(String userId) {
        UniversalResponse response = new UniversalResponse();
        List<String> groupIds = userGroupRepository.getDistinctGroupIds(userId);
        List<GroupDetail> groups = new ArrayList<>();
        for (String id : groupIds) {
            Optional<GroupTable> optionalGroupTable = groupRepository.findByGroupId(id);
            if (optionalGroupTable.isPresent()) {
                GroupTable table = optionalGroupTable.get();
                GroupDetail detail = new GroupDetail();
                detail.setGroupName(table.getGroupName());
                detail.setGroupId(table.getGroupId());
                detail.setAddedDate(table.getAddedDate());
                detail.setTotalAmountOwe(0);
                detail.setTotalAmountOweToYou(0);
                List<UserDto> userDtoList = new ArrayList<>();
                List<UserGroup> userGroups = userGroupRepository.findAllByGroupId(id);
                for (UserGroup userGroup : userGroups) {
                    Optional<User> optionalUser = userRepository.findByUserId(userGroup.getUserId());
                    if(optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        UserDto dto = new UserDto();
                        dto.setEmailId(user.getEmail());
                        dto.setUserName(user.getUserName());
                        dto.setUserId(user.getUserId());
                        userDtoList.add(dto);
                    }
                }
                detail.setUsers(userDtoList);
                ExpenseDetail expenseDetail = expenseService.getGroupExpenseDetailForAUserId(userId, table.getGroupId());
                detail.setTotalAmountOwe(expenseDetail.getTotalAmountOwe());
                detail.setTotalAmountOweToYou(expenseDetail.getTotalAmountOweToYou());
                groups.add(detail);
            }
        }
        ResponseCodeJson codeJson = new ResponseCodeJson();
        codeJson.setMessage("Fetched all groups");
        codeJson.setCode(200);
        response.setResponseCodeJson(codeJson);
        response.setList(groups);
        return response;
    }

    public UniversalResponse deleteGroup( String grpName){
        UniversalResponse response = new UniversalResponse();
        Optional<GroupTable> groupTable=groupRepository.findByGroupNameAndDeleted(grpName,false);
        if(groupTable.isPresent()){
            groupRepository.delete(groupTable.get());
            ResponseCodeJson codeJson = new ResponseCodeJson();
            codeJson.setCode(200);
            codeJson.setMessage("Group deleted");
            response.setResponseCodeJson(codeJson);
            return response;
        }
        ResponseCodeJson codeJson = new ResponseCodeJson();
        codeJson.setCode(401);
        codeJson.setMessage("Group not found");
        response.setResponseCodeJson(codeJson);

        return response;
    }
}
