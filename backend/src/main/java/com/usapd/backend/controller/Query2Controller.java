package com.usapd.backend.controller;

import com.usapd.backend.service.Query2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/query2")
public class Query2Controller {

    private final Query2Service query2Service;

    @Autowired
    public Query2Controller(Query2Service query2Service){
        this.query2Service = query2Service;
    }

    @GetMapping
    public String routeCheck(){
        return "API route is working!";
    }

    @GetMapping(path = "/getData")
    public String getListFromUsernames(@RequestParam("state") String state,
                                       @RequestParam("pollutant") String pollutant) {
        return query2Service.getQuery2Results(pollutant, state);
    }
}
