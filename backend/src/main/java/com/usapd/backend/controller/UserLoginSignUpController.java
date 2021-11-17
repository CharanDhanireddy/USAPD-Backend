package com.usapd.backend.controller;

import com.usapd.backend.entity.UserData;
import com.usapd.backend.entity.Users;
import com.usapd.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/user/")
public class UserLoginSignUpController {

    private final UserService userService;

    @Autowired
    public UserLoginSignUpController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup", consumes = "application/json")
    public ResponseEntity<Users> signUp(@RequestBody UserData userdata){

//        if(userService.alreadyExists(userdata.email_ID).isPresent())return "Email ID already Exists";
        String userEmail =  userService.signUp(userdata.email_ID, userdata.password);

//        if(userService.userNameAlreadyExists(userdata.email_ID).isPresent())return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Users userData =  userService.addUserData(userEmail, userdata.state_code);

        if(userData == null)return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return ResponseEntity.accepted().body(userData);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", consumes = "application/json")
    public ResponseEntity<Users> logIn(@RequestBody UserData userdata){
        Users userData = userService.getUserData(userdata.email_ID, userdata.password);
        if(userData == null)return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return ResponseEntity.accepted().body(userData);
    }

}
