SELECT dateData.year, dateData.weekday, avg(ap.arithmetic_mean) as meanValue 
FROM   (
              SELECT *
              FROM   vdhavaleswarapu.observation o where( o.pollutant_code =
                     (
                            SELECT pollutant_code
                            FROM   vdhavaleswarapu.pollutant p
                            WHERE  (
                                          p.pollutant_name = :pollutant ) ) )
              AND    (
                            o.site_code IN
                            (
                                   SELECT *
                                   FROM   (
                                                 SELECT site_code
                                                 FROM   vdhavaleswarapu.state state
                                                 JOIN   vdhavaleswarapu.county county
                                                 ON     county.state_code = state.state_code
                                                 JOIN   vdhavaleswarapu.site site
                                                 ON     site.county_code = county.county_code
                                                 WHERE  state_name = :state )))) ap 
JOIN
       (
              SELECT dc.date_id,
                     dc.year,
                     (dc.month
                            || '/'
                            || dc.day
                            || '/'
                            || dc.year) AS date_str,
                     to_char(to_date(dc.month
                            || '/'
                            || dc.day
                            || '/'
                            || dc.year, 'MM/DD/YYYY'), 'DAY') AS weekday from vdhavaleswarapu.datecollected dc ) dateData
ON     ap.date_id = dateData.date_id 
       group BY dateData.year, dateData.weekday 
       order BY dateData.weekday, dateData.year;