package com.usapd.backend.controller;

import com.usapd.backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        return "Hi";
    }

    @GetMapping(path = "/getList")
    public String check2() {
        return testService.getAllNames();
    }

    @GetMapping(path = "/getList/{username}")
    public String getListFromUsernames(@PathVariable("username") String username){
        return testService.getUsers(username);
    }
}
