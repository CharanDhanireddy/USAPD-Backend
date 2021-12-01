package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Query1Repository extends JpaRepository<Test, Integer> {

        @Query(value = "SELECT dc.year," +
                "       dc.week," +
                "       Avg(ap.arithmetic_mean) as meanValue " +
                "FROM   (" +
                "              SELECT *" +
                "              FROM   vdhavaleswarapu.observation o where( o.pollutant_code =" +
                "                     (" +
                "                            SELECT pollutant_code" +
                "                            FROM   vdhavaleswarapu.pollutant p" +
                "                            WHERE  (" +
                "                                          p.pollutant_name = :pollutant ) ) )" +
                "              AND    (" +
                "                            o.site_code IN" +
                "                            (" +
                "                                   SELECT *" +
                "                                   FROM   (" +
                "                                                 SELECT site_code" +
                "                                                 FROM   vdhavaleswarapu.state state" +
                "                                                 JOIN   vdhavaleswarapu.county county" +
                "                                                 ON     county.state_code = state.state_code" +
                "                                                 JOIN   vdhavaleswarapu.site site" +
                "                                                 ON     site.county_code = county.county_code" +
                "                                                 WHERE  state_name = :state )))) ap " +
                "JOIN" +
                "       (" +
                "              SELECT dc.date_id," +
                "                     dc.year," +
                "                     (dc.month" +
                "                            || '/'" +
                "                            || dc.day" +
                "                            || '/'" +
                "                            || dc.year) AS date_str," +
                "                     to_char(to_date(dc.month" +
                "                            || '/'" +
                "                            || dc.day" +
                "                            || '/'" +
                "                            || dc.year, 'MM/DD/YYYY'), 'WW') AS week from vdhavaleswarapu.datecollected dc" +
                "                   WHERE TO_DATE(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY')" +
                "                   BETWEEN TO_DATE(:startDate, 'MM/DD/YYYY')" +
                "                       AND TO_DATE(:endDate, 'MM/DD/YYYY') ) dc " +
                "ON     ap.date_id = dc.date_id " +
                "       group BY dc.year, dc.week " +
                "       order BY dc.year, dc.week",
            nativeQuery = true)
    List<JSONObject> getPollutantDataByState(String pollutant, String state, String startDate, String endDate);

}
