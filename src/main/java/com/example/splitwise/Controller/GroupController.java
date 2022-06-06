package com.example.splitwise.Controller;
import com.example.splitwise.Service.GroupService;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.CreateGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("/check/{group}")
    public ResponseCodeJson checkGroup(@PathVariable(value = "group") String groupName) {
        return groupService.checkGroup(groupName);
    }

    @PostMapping("/create")
    public UniversalResponse createGroup(@RequestBody CreateGroupDto dto) {
        return groupService.createGroup(dto);
    }

    @GetMapping("/getAll/{userId}")
    public UniversalResponse getGroups(@PathVariable(value = "userId") String userId) {
        return groupService.getGroups(userId);
    }

    @DeleteMapping("/deleteGroup/{groupName}")
        public UniversalResponse deleteGroup(@PathVariable(value = "groupName") String grpName){
           return groupService.deleteGroup(grpName);
        }

}
