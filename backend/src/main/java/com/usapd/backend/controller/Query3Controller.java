package com.usapd.backend.controller;

import com.usapd.backend.service.Query3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query3")
public class Query3Controller {

    private final Query3Service query3Service;

    @Autowired
    public Query3Controller(Query3Service query3Service){
        this.query3Service = query3Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames(@RequestParam("state") String state,
                                       @RequestParam("pollutant") String pollutant,
                                       @RequestParam("start") String startDate,
                                       @RequestParam("end") String endDate) {
        return query3Service.getQuery3Results(pollutant, state, startDate, endDate);
    }
}
