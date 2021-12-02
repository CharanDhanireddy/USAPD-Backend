package com.usapd.backend.service;

import com.usapd.backend.repository.Query4Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Query4Service {

    public final Query4Repository query4Repository;

    @Autowired
    public Query4Service(Query4Repository query4Repository){
        this.query4Repository = query4Repository;
    }

    public String getQuery4Results(String pollutant, String state_list, String startDate, String endDate){
        String state_stringList[] = state_list.split(",");
        List<String> state_arrayList = Arrays.asList(state_stringList);
        List<JSONObject> q4List = query4Repository.getMonthlyData(pollutant, state_arrayList, startDate, endDate);
        return q4List.toString();
    }
}
