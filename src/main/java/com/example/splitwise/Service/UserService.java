package com.example.splitwise.Service;
import com.example.splitwise.Model.User;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.common.ResponseCodeJson;
import com.example.splitwise.common.UniversalResponse;
import com.example.splitwise.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UniversalResponse addUser(UserDto dto) {
        UniversalResponse response = new UniversalResponse();
        Optional<User> optionalUser = userRepository.findByEmailAndDeleted(dto.getEmailId(), false);
        if (optionalUser.isPresent()) {
            ResponseCodeJson responseCodeJson = new ResponseCodeJson();
            responseCodeJson.setCode(401);
            responseCodeJson.setMessage("User already present");
            response.setResponseCodeJson(responseCodeJson);
            return response;
        }
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmailId());
        user.setPassword(dto.getPassword());
        user.setUserId(UUID.randomUUID().toString());
        user.setDeleted(false);
        userRepository.save(user);
        dto.setUserId(user.getUserId());
        ResponseCodeJson responseCodeJson = new ResponseCodeJson();
        responseCodeJson.setCode(200);
        responseCodeJson.setMessage("User created successfully");
        response.setObject(dto);
        response.setResponseCodeJson(responseCodeJson);
        return response;
    }

    public UniversalResponse checkUserPresent(String email) {
        UniversalResponse response = new UniversalResponse();
        ResponseCodeJson codeJson = new ResponseCodeJson();
        Optional<User> optionalUser = userRepository.findByEmailAndDeleted(email, false);
        if (!optionalUser.isPresent()) {
            codeJson.setCode(404);
            codeJson.setMessage("User not present");
            response.setResponseCodeJson(codeJson);
            return response;
        }
        codeJson.setCode(200);
        codeJson.setMessage("User present");
        response.setResponseCodeJson(codeJson);
        response.setObject(optionalUser.get());
        return response;

    }

    public UniversalResponse registeredUser(String email,String password){
        UniversalResponse universalResponse=new UniversalResponse();
        Optional<User> optionalUser=userRepository.findByEmailAndPassword(email,password);
        if(optionalUser.isPresent()){
            ResponseCodeJson responseCodeJson=new ResponseCodeJson();
            responseCodeJson.setCode(200);
            responseCodeJson.setMessage("logIn successful");
            universalResponse.setObject(optionalUser.get());
             universalResponse.setResponseCodeJson(responseCodeJson);
             return  universalResponse;
        }
        ResponseCodeJson responseCodeJson=new ResponseCodeJson();
        responseCodeJson.setCode(400);
        responseCodeJson.setMessage("Invalid email and password");
        universalResponse.setResponseCodeJson(responseCodeJson);
        return universalResponse;
    }
}
