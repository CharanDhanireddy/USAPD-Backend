package com.usapd.backend.service;

import com.usapd.backend.repository.Query5Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query5Service {

    public final Query5Repository query5Repository;

    @Autowired
    public Query5Service(Query5Repository query5Repository){
        this.query5Repository = query5Repository;
    }

    public String getQuery5Results(String state, Integer threshold, String startDate, String endDate){
        List<JSONObject> q5List = query5Repository.getBadAQIDayCount(state, threshold, startDate, endDate);
        return q5List.toString();
    }
}
