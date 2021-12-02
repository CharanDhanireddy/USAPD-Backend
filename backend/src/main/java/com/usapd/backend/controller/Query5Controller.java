package com.usapd.backend.controller;

import com.usapd.backend.service.Query5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query5")
public class Query5Controller {

    private final Query5Service query5Service;

    @Autowired
    public Query5Controller(Query5Service query5Service){
        this.query5Service = query5Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames(@RequestParam("state") String state,
                                       @RequestParam("threshold") Integer threshold,
                                       @RequestParam("start") String startDate,
                                       @RequestParam("end") String endDate) {
        return query5Service.getQuery5Results(state, threshold, startDate, endDate);
    }
}
