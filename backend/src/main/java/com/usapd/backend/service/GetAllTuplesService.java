package com.usapd.backend.service;

import com.usapd.backend.repository.getAllTuplesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAllTuplesService {

    public final getAllTuplesRepository getAllTuplesRepo;

    @Autowired
    public GetAllTuplesService(getAllTuplesRepository getAllTuplesRepo){
        this.getAllTuplesRepo = getAllTuplesRepo;
    }

    public String getAllTuplesResults() {
        String numTuples = getAllTuplesRepo.getAllTuples();
        System.out.println(numTuples);
        return numTuples + "";
    }
}
