package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Query2Repository extends JpaRepository<Test, Integer> {

    @Query(value = "SELECT to_char(ap.date_str2, 'YYYY') YEAR, to_char(ap.date_str2, 'DAY') DAY, round(AVG(ap.arithmetic_mean), 5) AS meanValue FROM ( SELECT * FROM vdhavaleswarapu.observation o WHERE ( o.pollutant_code = ( SELECT pollutant_code FROM vdhavaleswarapu.pollutant p WHERE ( p.pollutant_name = :pollutant ) ) ) AND ( o.site_code IN ( SELECT * FROM ( SELECT site_code FROM vdhavaleswarapu.state state JOIN vdhavaleswarapu.county county ON county.state_code = state.state_code JOIN vdhavaleswarapu.site site ON site.county_code = county.county_code WHERE state_name = :state ) ) ) ) ap GROUP BY to_char(ap.date_str2, 'YYYY'), to_char(ap.date_str2, 'DAY') ORDER BY DAY, YEAR",
            nativeQuery = true)
    List<JSONObject> getDayOfWeekPollutantDataByState(String pollutant, String state);
}
