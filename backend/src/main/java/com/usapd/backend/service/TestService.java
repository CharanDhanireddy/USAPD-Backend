package com.usapd.backend.service;

import com.usapd.backend.entity.Test;
import com.usapd.backend.repository.TestRepository;
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

    public List<Test> getAllNames(){
        return (List<Test>) testRepository.findAll();
    }
}
