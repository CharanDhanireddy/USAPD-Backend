package com.usapd.backend.service;

import com.usapd.backend.repository.Query3Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query3Service {

    public final Query3Repository query3Repository;

    @Autowired
    public Query3Service(Query3Repository query3Repository){
        this.query3Repository = query3Repository;
    }

    public String getQuery3Results(String pollutant, String state, String startDate, String endDate){
        List<JSONObject> q3List = query3Repository.getMaxHourData(pollutant, state, startDate, endDate);
        return q3List.toString();
    }
}
