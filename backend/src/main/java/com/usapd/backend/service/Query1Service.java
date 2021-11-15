package com.usapd.backend.service;

import com.usapd.backend.repository.Query1Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query1Service {

    public final Query1Repository query1Repository;

    @Autowired
    public Query1Service(Query1Repository query1Repository){
        this.query1Repository = query1Repository;
    }

    public String getQuery1Results(String pollutant, String state){
//        query1Repository.createViewForStateSiteMapping(state);
//        query1Repository.createViewForObsPollutant(pollutant);
//        query1Repository.createViewForDateWeek();
          List<JSONObject> q1List = query1Repository.getPollutantDataByState(pollutant, state);

        return q1List.toString();
    }
}
