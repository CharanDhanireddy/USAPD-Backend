package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query5Repository extends JpaRepository<Test, Integer> {

    @Query(value = "SELECT * FROM ((SELECT MONTH, YEAR, count(avgAQI) AS DAYCOUNT FROM (SELECT to_char(aqiData.dateVal, 'YYYY') year, aqiData.dateVal, to_char(aqiData.dateVal, 'MM') month, avg(aqiData.aqi) avgAQI FROM (SELECT date_str2 as dateVal, aqi FROM VDHAVALESWARAPU.observation o WHERE (o.site_code in (SELECT * FROM (SELECT site_code FROM VDHAVALESWARAPU.State state JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code WHERE state_name = :state))) AND o.date_str2 BETWEEN :startDate AND :endDate ) aqiData WHERE aqiData.aqi != 0 GROUP BY to_char(aqiData.dateVal, 'YYYY'), to_char(aqiData.dateVal, 'MM'), aqiData.dateVal) WHERE avgAQI >= :threshold GROUP BY MONTH, YEAR) UNION (SELECT MONTH, YEAR, 0 FROM (SELECT to_char(aqiData.dateVal, 'YYYY') year, aqiData.dateVal, to_char(aqiData.dateVal, 'MM') month, avg(aqiData.aqi) avgAQI FROM (SELECT date_str2 as dateVal, aqi FROM VDHAVALESWARAPU.observation o WHERE (o.site_code in (SELECT * FROM (SELECT site_code FROM VDHAVALESWARAPU.State state JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code WHERE state_name = :state))) AND o.date_str2 BETWEEN :startDate AND :endDate ) aqiData WHERE aqiData.aqi != 0 GROUP BY to_char(aqiData.dateVal, 'YYYY'), to_char(aqiData.dateVal, 'MM'), aqiData.dateVal) GROUP BY MONTH, YEAR HAVING max(avgAQI) < :threshold)) ORDER BY YEAR, MONTH",
            nativeQuery = true)
    List<JSONObject> getBadAQIDayCount(String state, Integer threshold, String startDate, String endDate);

}
