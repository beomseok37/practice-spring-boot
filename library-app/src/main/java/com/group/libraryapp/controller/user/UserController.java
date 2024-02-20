package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.UserCreateRequest;
import com.group.libraryapp.dto.user.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request){
        users.add(new User(request.getName(),request.getAge()));
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers(){
        List<UserResponse> result = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            result.add(new UserResponse(i+1,users.get(i)));
        }

        return result;
    }
}
