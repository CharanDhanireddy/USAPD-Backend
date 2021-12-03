SELECT dateData.year,
       dateData.month,
       siteData.state_name AS state,
       Avg(multiStatePollutantData.arithmetic_mean) AS meanValue
FROM
    (SELECT *
     FROM vdhavaleswarapu.observation o
     WHERE (o.pollutant_code =
            (SELECT pollutant_code
             FROM vdhavaleswarapu.pollutant p
             WHERE (p.pollutant_name = :pollutant)))
       AND (o.site_code IN
            (SELECT site_code
             FROM
                 (SELECT site_code
                  FROM vdhavaleswarapu.state state
                           JOIN vdhavaleswarapu.county county ON county.state_code = state.state_code
                           JOIN vdhavaleswarapu.site site ON site.county_code = county.county_code
                  WHERE state_name IN :state_list )))) multiStatePollutantData
        JOIN
    (SELECT dc.date_id,
            dc.year,
            To_char(dc.datestr, 'MM') AS MONTH
     FROM vdhavaleswarapu.datecollected dc
     WHERE dc.datestr BETWEEN :startDate AND :endDate ) dateData ON multiStatePollutantData.date_id = dateData.date_id
        JOIN
    (SELECT site_code,
            state_name
     FROM vdhavaleswarapu.state s
              JOIN vdhavaleswarapu.site si ON s.state_code = si.state_code) siteData ON siteData.site_code = multiStatePollutantData.site_code GROUP  BY siteData.state_name,
                                                                                                                                                         dateData.year,
                                                                                                                                                         dateData.month
ORDER  BY siteData.state_name,
          dateData.month,
          dateData.year;