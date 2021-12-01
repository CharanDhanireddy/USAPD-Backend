package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query2Repository extends JpaRepository<Test, Integer> {

//    @Query(value = "create or replace view getStateSites1 AS Select site_code " +
//            "from VDHAVALESWARAPU.State state " +
//            "join VDHAVALESWARAPU.County county on county.state_code = state.state_code " +
//            "join VDHAVALESWARAPU.Site site on site.county_code = county.county_code " +
//            "where state_name = :state",
//            nativeQuery = true)
//    void createViewForStateSiteMapping(
//            @Param("state") String state);

//    @Query(value = "Create or replace view allPollObs AS" +
//            "Select *" +
//            "from VDHAVALESWARAPU.observation o" +
//            "where (" +
//            "    o.pollutant_code = (Select pollutant_code" +
//            "                        from VDHAVALESWARAPU.pollutant p" +
//            "                        where (" +
//            "                            p.pollutant_name = ':pollutant'" +
//            "                            )" +
//            "                        )" +
//            "    ) and (" +
//            "        o.site_code in (Select *" +
//            "                        from getStateSites1)" +
//            "    )",
//            nativeQuery = true)
//    void createViewForObsPollutant(
//            @Param("pollutant") String pollutant);

//    @Query(value = "Create or replace View dateData1 as" +
//            "Select dc.date_id," +
//            "    dc.year," +
//            "    (dc.month || '/' || dc.day || '/' || dc.year) as date_str," +
//            "    to_char(to_date(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY'), 'WW') as week" +
//            "from VDHAVALESWARAPU.datecollected dc" +
//            "where dc.year between (2016 and 2018)",
//            nativeQuery = true)
//    void createViewForDateWeek();


//    @Query(value = "Select dc.year," +
//            "    dc.week," +
//            "    avg(ap.arithmetic_mean)" +
//            "from allPollObs ap" +
//            "    join dateData1 dc on ap.date_id = dc.date_id" +
//            "group by dc.year, dc.week" +
//            "order by dc.year, dc.week",
//            nativeQuery = true)
//    List<JSONObject> getPollutantDataByState();

    @Query(value = "SELECT dateData.year, dateData.weekday, avg(ap.arithmetic_mean) as meanValue FROM   ( SELECT * FROM   vdhavaleswarapu.observation o where( o.pollutant_code = ( SELECT pollutant_code FROM   vdhavaleswarapu.pollutant p WHERE  ( p.pollutant_name = :pollutant ) ) ) AND    ( o.site_code IN ( SELECT * FROM   ( SELECT site_code FROM   vdhavaleswarapu.state state JOIN   vdhavaleswarapu.county county ON     county.state_code = state.state_code JOIN   vdhavaleswarapu.site site ON     site.county_code = county.county_code WHERE  state_name = :state )))) ap JOIN ( SELECT dc.date_id, dc.year, (dc.month || '/' || dc.day || '/' || dc.year) AS date_str, to_char(to_date(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY'), 'DAY') AS weekday from vdhavaleswarapu.datecollected dc ) dateData ON     ap.date_id = dateData.date_id group BY dateData.year, dateData.weekday order BY dateData.weekday, dateData.year",
            nativeQuery = true)
    List<JSONObject> getDayOfWeekPollutantDataByState(String pollutant, String state);
}
