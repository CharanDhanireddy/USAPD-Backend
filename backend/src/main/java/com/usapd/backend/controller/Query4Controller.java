package com.usapd.backend.controller;

import com.usapd.backend.service.Query4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query4")
public class Query4Controller {

    private final Query4Service query4Service;

    @Autowired
    public Query4Controller(Query4Service query4Service){
        this.query4Service = query4Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames(@RequestParam("state_list") String state_list,
                                       @RequestParam("pollutant") String pollutant,
                                       @RequestParam("start") String startDate,
                                       @RequestParam("end") String endDate) {
        return query4Service.getQuery4Results(pollutant, state_list, startDate, endDate);
    }
}
