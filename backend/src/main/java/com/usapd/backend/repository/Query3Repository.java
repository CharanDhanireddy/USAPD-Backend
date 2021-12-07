package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query3Repository extends JpaRepository<Test, Integer> {

    @Query(value = "SELECT to_char(pollutantStateData.date_str2, 'YYYY') YEAR, pollutantStateData.max_hour, COUNT(pollutantStateData.max_hour) AS COUNT FROM ( SELECT * FROM vdhavaleswarapu.observation o WHERE ( o.pollutant_code = ( SELECT pollutant_code FROM vdhavaleswarapu.pollutant p WHERE ( p.pollutant_name = :pollutant ) ) ) AND ( o.site_code IN ( SELECT site_code FROM vdhavaleswarapu.state state JOIN vdhavaleswarapu.county county ON county.state_code = state.state_code JOIN vdhavaleswarapu.site site ON site.county_code = county.county_code WHERE ( state_name = :state ) ) ) AND ( o.date_str2 BETWEEN :startDate AND :endDate ) ) pollutantStateData GROUP BY to_char(pollutantStateData.date_str2, 'YYYY'), pollutantStateData.max_hour ORDER BY pollutantStateData.max_hour, YEAR", nativeQuery = true)
    List<JSONObject> getMaxHourData(String pollutant, String state, String startDate, String endDate);

}
