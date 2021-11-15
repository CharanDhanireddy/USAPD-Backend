package com.usapd.backend.controller;

import com.usapd.backend.entity.UserData;
import com.usapd.backend.entity.Users;
import com.usapd.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user/")
public class UserLoginSignUpController {

    private final UserService userService;

    @Autowired
    public UserLoginSignUpController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup", consumes = "application/json")
    public Users signUp(@RequestBody UserData userdata){

//        if(userService.alreadyExists(userdata.email_ID).isPresent())return "Email ID already Exists";
        String userEmail =  userService.signUp(userdata.email_ID, userdata.password);

//        if(userService.userNameAlreadyExists(userdata.user_name).isPresent())return "Username already Exists";
        Users user =  userService.addUserData(userdata.email_ID, userdata.user_name, userdata.state_code);

        return user;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", consumes = "application/json")
    public Users logIn(@RequestBody UserData userdata){
        return userService.getUserData(userdata.email_ID, userdata.password);
    }

}
