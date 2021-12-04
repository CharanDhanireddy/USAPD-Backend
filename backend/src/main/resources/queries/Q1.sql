SELECT YEAR,
    season,
    round(avg(meanval), 3) AS meanValue
FROM
    (SELECT dateData.year,
    dateData.week,
    avg(ap.arithmetic_mean) AS meanval,
    CASE
    WHEN dateData.week between 11 and 24 THEN 'Spring'
    WHEN dateData.week between 25 and 37 THEN 'Summer'
    WHEN dateData.week between 38 and 50 THEN 'Fall'
    WHEN dateData.week >= 51 OR dateData.week < 11 THEN 'Winter'
    END AS season
    FROM
    (SELECT arithmetic_mean,
    date_id
    FROM VDHAVALESWARAPU.observation o
    WHERE (o.pollutant_code =
    (SELECT pollutant_code
    FROM VDHAVALESWARAPU.pollutant p
    WHERE (p.pollutant_name = :pollutant) ))
    AND (o.site_code in
    (SELECT site_code
    FROM
    (SELECT site_code
    FROM VDHAVALESWARAPU.State state
    JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code
    JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code
    WHERE state_name = :state)))) ap
    JOIN
    (SELECT dc.date_id,
    dc.year,
    to_char(dc.datestr, 'WW') WEEK
    FROM VDHAVALESWARAPU.datecollected dc where dc.datestr between :startDate and :endDate) dateData ON ap.date_id = dateData.date_id
    GROUP BY dateData.year,
    dateData.week
    ORDER BY dateData.year)
GROUP BY season,
    YEAR
ORDER BY YEAR,
    season;