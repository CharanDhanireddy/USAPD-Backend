SELECT
   to_char(pollutantStateData.date_str2, 'YYYY') YEAR,
   pollutantStateData.max_hour,
   COUNT(pollutantStateData.max_hour) AS COUNT 
FROM
   (
      SELECT
         * 
      FROM
         vdhavaleswarapu.observation o 
      WHERE
         (
            o.pollutant_code = 
            (
               SELECT
                  pollutant_code 
               FROM
                  vdhavaleswarapu.pollutant p 
               WHERE
                  (
                     p.pollutant_name = :pollutant 
                  )
            )
         )
         AND 
         (
            o.site_code IN 
            (
               SELECT
                  site_code 
               FROM
                  vdhavaleswarapu.state state 
                  JOIN
                     vdhavaleswarapu.county county 
                     ON county.state_code = state.state_code 
                  JOIN
                     vdhavaleswarapu.site site 
                     ON site.county_code = county.county_code 
               WHERE
                  (
                     state_name = :state 
                  )
            )
         )
         AND 
         (
            o.date_str2 BETWEEN :startDate AND :endDate 
         )
   )
   pollutantStateData 
GROUP BY
   to_char(pollutantStateData.date_str2, 'YYYY'),
   pollutantStateData.max_hour 
ORDER BY
   pollutantStateData.max_hour,
   YEAR;
