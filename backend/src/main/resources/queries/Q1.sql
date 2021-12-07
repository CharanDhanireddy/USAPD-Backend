SELECT YEAR,
    season,
    round(avg(meanval), 3) AS meanValue
FROM
    (SELECT TO_CHAR(ap.date_str2, 'YYYY') as year,
    TO_CHAR(ap.date_str2, 'WW') as week,
    avg(ap.arithmetic_mean) AS meanval,
    CASE
    WHEN TO_CHAR(ap.date_str2, 'WW') between 11 and 24 THEN 'Spring'
    WHEN TO_CHAR(ap.date_str2, 'WW') between 25 and 37 THEN 'Summer'
    WHEN TO_CHAR(ap.date_str2, 'WW') between 38 and 50 THEN 'Fall'
    WHEN TO_CHAR(ap.date_str2, 'WW') >= 51 OR TO_CHAR(ap.date_str2, 'WW') < 11 THEN 'Winter'
    END AS season
    FROM
    (SELECT arithmetic_mean,
    date_str2
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
    GROUP BY TO_CHAR(ap.date_str2, 'YYYY'),
    TO_CHAR(ap.date_str2, 'WW')
    ORDER BY year)
GROUP BY season,
    YEAR
ORDER BY YEAR,
    season;
