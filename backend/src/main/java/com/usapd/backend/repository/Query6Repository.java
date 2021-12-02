package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query6Repository extends JpaRepository<Test, Integer> {

    @Query(value = "SELECT rownum AS rank, state, aqi AS avgAqi FROM (SELECT dateData.year, siteState.state_name AS state, avg(aqiData.aqi) AS aqi FROM (SELECT aqi, date_id, site_code FROM VDHAVALESWARAPU.observation o WHERE (o.site_code in (SELECT site_code FROM VDHAVALESWARAPU.State state JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code))) aqiData JOIN (SELECT dc.date_id, dc.year FROM VDHAVALESWARAPU.datecollected dc) dateData ON aqiData.date_id = dateData.date_id JOIN (SELECT site_code, state_name FROM VDHAVALESWARAPU.state s JOIN VDHAVALESWARAPU.site si ON s.state_code = si.state_code) siteState ON siteState.site_code = aqiData.site_code WHERE dateData.year = '2020' GROUP BY siteState.state_name, dateData.year ORDER BY aqi)",
            nativeQuery = true)
    List<JSONObject> getAQIRanks();
}
