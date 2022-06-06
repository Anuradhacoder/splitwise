package com.example.splitwise.Controller;

import com.example.splitwise.Model.UserGroup;
import com.example.splitwise.Service.GroupService;
import com.example.splitwise.Service.UserService;
import com.example.splitwise.Service.userGroupService;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private userGroupService userGroupServicee;
    @PostMapping("/create")
    public UniversalResponse addUser(@RequestBody UserDto user)
    {
        return userService.addUser(user);
    }

    @GetMapping("/check/{email}")
    public UniversalResponse checkUser(@PathVariable(value = "email")String email) {
        return userService.checkUserPresent(email);
    }

    @PostMapping("/userGroupDetails/{grpName}/{email}")
    public UniversalResponse userGroupDetails(@PathVariable(value = "grpName") String grpName,@PathVariable(value = "email") String email){
        return userGroupServicee.UserGroupDetails(grpName,email);
    }
    @GetMapping("/registeredUser/{email}/{password}")
    public UniversalResponse regiseteredUser(@PathVariable(value = "email") String email,@PathVariable(value = "password") String password){
        return userService.registeredUser(email,password);
    }
}
