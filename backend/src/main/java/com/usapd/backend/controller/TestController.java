package com.usapd.backend.controller;

import com.usapd.backend.entity.Test;
import com.usapd.backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping
    public String check(){
        return "Hey!";
    }


    @GetMapping(path = "/getList")
    public List<Test> check2(){
        return testService.getAllNames();
    }
}
