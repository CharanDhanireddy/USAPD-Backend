package com.usapd.backend.controller;

import com.usapd.backend.service.Query7Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query7")
public class Query7Controller {

    private final Query7Service query7Service;

    @Autowired
    public Query7Controller(Query7Service query7Service){
        this.query7Service = query7Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getPercentageGrowth(@RequestParam("state") String state) {
        return query7Service.getQuery7Results(state);
    }
}
