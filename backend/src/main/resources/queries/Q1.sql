SELECT dc.year,
       dc.week,
       Avg(ap.arithmetic_mean) AS meanValue
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
          (dc.month || '/' || dc.day || '/' || dc.year) AS date_str,
          to_char(to_date(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY'), 'WW') AS WEEK
   FROM vdhavaleswarapu.datecollected dc
   WHERE TO_DATE(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY') BETWEEN TO_DATE(:startDate, 'MM/DD/YYYY') AND TO_DATE(:endDate, 'MM/DD/YYYY') ) dc ON ap.date_id = dc.date_id
GROUP BY dc.year,
         dc.week
ORDER BY dc.year,
         dc.week