package com.usapd.backend.controller;

import com.usapd.backend.service.Query6Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query6")
public class Query6Controller {

    private final Query6Service query6Service;

    @Autowired
    public Query6Controller(Query6Service query6Service){
        this.query6Service = query6Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames() {
        return query6Service.getQuery6Results();
    }
}
