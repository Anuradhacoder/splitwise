package com.example.splitwise.Service;

import com.example.splitwise.Model.GroupTable;
import com.example.splitwise.Model.User;
import com.example.splitwise.Model.UserGroup;
import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserGroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userGroupService {
     @Autowired
     private UserGroupRepository userGroupRepository;
     @Autowired
     private GroupRepository groupRepository;
     @Autowired
     private UserRepository userRepository;

     public UniversalResponse UserGroupDetails(String groupId,String userId){
         UniversalResponse response=new UniversalResponse();
         Optional<UserGroup> optionalUserGroup=userGroupRepository.findByUserIdAndGroupId(userId,groupId);
         if(optionalUserGroup.isPresent()){
             ResponseCodeJson responseCodeJson=new ResponseCodeJson();
             responseCodeJson.setCode(401);
             responseCodeJson.setMessage("groupId and userId already exist");
             response.setResponseCodeJson(responseCodeJson);
             return response;
         }
         Optional<GroupTable> optionalGroupTable=groupRepository.findByGroupId(groupId);
         Optional<User> optionalUser=userRepository.findByUserId(userId);
         if (optionalGroupTable.isEmpty() || optionalUser.isEmpty()) {
             ResponseCodeJson responseCodeJson=new ResponseCodeJson();
             responseCodeJson.setCode(400);
             responseCodeJson.setMessage("groupId not found or userId not found");
             response.setResponseCodeJson(responseCodeJson);
             return response;
         }
         GroupTable groupTable = optionalGroupTable.get();
         User user = optionalUser.get();
         UserGroup userGroupDetails=new UserGroup();
         userGroupDetails.setGroupId(groupId);
         userGroupDetails.setUserId(userId);
         userGroupDetails.setGroupName(groupTable.getGroupName());
         userGroupDetails.setUserName(user.getUserName());
         userGroupRepository.save(userGroupDetails);
         ResponseCodeJson responseCodeJson=new ResponseCodeJson();
         responseCodeJson.setCode(200);
         responseCodeJson.setMessage("Data inserted successfully");
         response.setResponseCodeJson(responseCodeJson);
         response.setObject(userGroupDetails);
         return response;

     }
}
