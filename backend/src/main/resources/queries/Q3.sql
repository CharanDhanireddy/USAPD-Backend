SELECT dateData.year,
       pollutantStateData.max_hour,
       count(pollutantStateData.max_hour) AS COUNT
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
    WHERE state_name = :state )))) pollutantStateData
    JOIN
    (SELECT dc.date_id,
    dc.year,
    to_char(dc.datestr, 'WW') AS WEEK
    FROM vdhavaleswarapu.datecollected dc
    WHERE dc.datestr BETWEEN :startdate AND :endDate) dateData ON pollutantStateData.date_id = dateData.date_id
GROUP BY dateData.year,
    pollutantStateData.max_hour
ORDER BY pollutantStateData.max_hour,
    dateData.year;