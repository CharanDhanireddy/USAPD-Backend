package com.usapd.backend.controller;

import com.usapd.backend.service.Query1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query1")
public class Query1Controller {

    private final Query1Service query1Service;

    @Autowired
    public Query1Controller(Query1Service query1Service){
        this.query1Service = query1Service;
    }

    @GetMapping
    public String check(){
        return "Hi";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames(@RequestParam("state") String state,
                                       @RequestParam("pollutant") String pollutant,
                                       @RequestParam("start") String startDate,
                                       @RequestParam("end") String endDate) {
        System.out.println(startDate);
        System.out.println(endDate);
        return query1Service.getQuery1Results(pollutant, state, startDate, endDate);
    }
}
