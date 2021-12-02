package com.usapd.backend.service;

import com.usapd.backend.repository.Query6Repository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query6Service {

    public final Query6Repository query6Repository;

    @Autowired
    public Query6Service(Query6Repository query6Repository){
        this.query6Repository = query6Repository;
    }

    public String getQuery6Results(){
        List<JSONObject> q6List = query6Repository.getAQIRanks();
        return q6List.toString();
    }
}
