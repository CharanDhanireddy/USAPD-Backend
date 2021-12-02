package com.usapd.backend.controller;

import com.usapd.backend.service.GetAllTuplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/getAllTuples")
public class GetAllTuplesController {

    private final GetAllTuplesService getAllTuplesService;

    @Autowired
    public GetAllTuplesController(GetAllTuplesService getAllTuplesService){
        this.getAllTuplesService = getAllTuplesService;
    }

    @GetMapping
    public String check(){
        return "Hi";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames() {
        return getAllTuplesService.getAllTuplesResults();
    }
}
