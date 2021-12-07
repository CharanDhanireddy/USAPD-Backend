package com.usapd.backend.service;

import com.usapd.backend.repository.Query7Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query7Service {

    public final Query7Repository query7Repository;

    @Autowired
    public Query7Service(Query7Repository query7Repository){
        this.query7Repository = query7Repository;
    }

    public String getQuery7Results(String state){
        List<JSONObject> q7List = query7Repository.getPercentageGrowth(state);
        return q7List.toString();
    }
}
