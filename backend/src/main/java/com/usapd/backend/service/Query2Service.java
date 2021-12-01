package com.usapd.backend.service;

import com.usapd.backend.repository.Query2Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query2Service {

    public final Query2Repository query2Repository;

    @Autowired
    public Query2Service(Query2Repository query2Repository){
        this.query2Repository = query2Repository;
    }

    public String getQuery2Results(String pollutant, String state){
//        query1Repository.createViewForStateSiteMapping(state);
//        query1Repository.createViewForObsPollutant(pollutant);
//        query1Repository.createViewForDateWeek();
        List<JSONObject> q2List = query2Repository.getDayOfWeekPollutantDataByState(pollutant, state);

        return q2List.toString();
    }
}
