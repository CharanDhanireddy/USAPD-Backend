package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query7Repository extends JpaRepository<Test, Integer> {
    @Query(value = "select a.state_name, b.year, pollutant_data.pollutant, pollutant_data.percentage_diff as pollutant_percentage_diff, round((b.population-a.population)*100/(a.population),5) population_percentage_diff from VDHAVALESWARAPU.population_total a, VDHAVALESWARAPU.population_total b, (select :state as State_pol, a.pollutant, b.year as bigYear, a.year as smallYear, round(((b.avgPollutant - a.avgPollutant)*100)/(a.avgPollutant),5) as percentage_diff from ( Select pol.pollutant_name AS pollutant, round(avg(o.arithmetic_mean),6) as avgPollutant, to_char(o.date_str2, 'YYYY') year from VDHAVALESWARAPU.observation o natural join VDHAVALESWARAPU.pollutant pol where ( ( o.site_code in (Select site_code from (Select site_code from VDHAVALESWARAPU.State state join VDHAVALESWARAPU.County county on county.state_code = state.state_code join VDHAVALESWARAPU.Site site on site.county_code = county.county_code where state_name = :state) ) ) ) group by pol.pollutant_name, to_char(o.date_str2, 'YYYY') order by pol.pollutant_name, to_char(o.date_str2, 'YYYY') ) a, ( Select pol.pollutant_name AS pollutant, round(avg(o.arithmetic_mean),6) as avgPollutant, to_char(o.date_str2, 'YYYY') year from VDHAVALESWARAPU.observation o natural join VDHAVALESWARAPU.pollutant pol where ( ( o.site_code in (Select site_code from (Select site_code from VDHAVALESWARAPU.State state join VDHAVALESWARAPU.County county on county.state_code = state.state_code join VDHAVALESWARAPU.Site site on site.county_code = county.county_code where state_name = :state) ) ) ) group by pol.pollutant_name, to_char(o.date_str2, 'YYYY') order by pol.pollutant_name, to_char(o.date_str2, 'YYYY') ) b where a.year + 1 = b.year and a.pollutant = b.pollutant) pollutant_data where a.year = pollutant_data.smallYear and b.year= pollutant_data.bigYear and a.state_name=pollutant_data.State_pol and b.state_name=a.state_name",
            nativeQuery = true)
    List<JSONObject> getPercentageGrowth(String state);
}
