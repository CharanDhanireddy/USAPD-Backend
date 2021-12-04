SELECT dateData.year,
       dateData.weekday,
       round(avg(ap.arithmetic_mean), 3) AS meanValue
FROM
    (SELECT *
     FROM vdhavaleswarapu.observation o where(o.pollutant_code =
                                              (SELECT pollutant_code
                                               FROM vdhavaleswarapu.pollutant p
                                               WHERE (p.pollutant_name = :pollutant) ))
                                          AND (o.site_code IN
                                               (SELECT *
                                                FROM
                                                    (SELECT site_code
                                                     FROM vdhavaleswarapu.state state
                                                              JOIN vdhavaleswarapu.county county ON county.state_code = state.state_code
                                                              JOIN vdhavaleswarapu.site site ON site.county_code = county.county_code
                                                     WHERE state_name = :state )))) ap
        JOIN
    (SELECT dc.date_id,
            dc.year,
            to_char(dc.datestr, 'DAY') AS weekday
     FROM vdhavaleswarapu.datecollected dc) dateData ON ap.date_id = dateData.date_id
GROUP BY dateData.year,
         dateData.weekday
ORDER BY dateData.weekday,
         dateData.year;