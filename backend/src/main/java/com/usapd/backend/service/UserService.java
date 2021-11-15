package com.usapd.backend.service;

import com.usapd.backend.entity.UserCredentials;
import com.usapd.backend.entity.Users;
import com.usapd.backend.repository.UserRepository;
import com.usapd.backend.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public final UserRepository userRepository;
    public final UserCredentialRepository userCredentialRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserCredentialRepository userCredentialRepository) {
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    public String signUp(String email_ID, String password){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.EMAIL_ID = email_ID;
        userCredentials.PASSWORD = password;
        UserCredentials savedUserCredentials = userCredentialRepository.saveAndFlush(userCredentials);
        return savedUserCredentials.toString();
    }

    public Users getUserData(String email_ID, String password){
        Optional<UserCredentials> uc = userCredentialRepository.findById(email_ID);
        System.out.println(uc.get().PASSWORD +  password);
        if(uc.isPresent() && uc.get().PASSWORD.equals(password)){
            System.out.println("HERE");
            return userRepository.findById(email_ID).get();
        }
        return null;
    }

    public Users addUserData(String email_ID, String user_name, Integer state_code){

        Users user = new Users();
        user.EMAIL_ID = email_ID;
        user.USER_NAME = user_name;
        user.STATE_CODE = state_code;
        Users savedUserData = userRepository.saveAndFlush(user);
        return savedUserData;
    }

//    public Optional<Users> userNameAlreadyExists(String user_name){
//        Optional<Users> a = userRepository.findById(user_name);
//        return a;
//    }

//    public Optional<UserCredentials> alreadyExists(String email_ID){
//        Optional<UserCredentials> a = userCredentialRepository.findById(email_ID);
//        System.out.println(a);
//        return a;
//    }
}
