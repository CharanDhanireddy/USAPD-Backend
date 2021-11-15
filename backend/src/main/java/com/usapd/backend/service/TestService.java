package com.usapd.backend.service;

import com.usapd.backend.entity.Test;
import com.usapd.backend.repository.TestRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    public final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    public String getAllNames(){
        List<Test> allItems = testRepository.findAll();
        return allItems.toString();
    }

    public String getUsers(String username){
        List<JSONObject> allItems = testRepository.selectUsersByUsername(username);
        return allItems.toString();
    }

}
