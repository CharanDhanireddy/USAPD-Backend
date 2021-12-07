SELECT
   to_char(multiStatePollutantData.date_str2, 'MM') as month,
   extract(year 
from
   multiStatePollutantData.date_str2) as year,
   siteData.state_name AS state,
   round(Avg(multiStatePollutantData.arithmetic_mean), 5) AS meanValue 
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
                        state_name IN :state_list 
                  )
            )
         )
         AND 
         (
            o.date_str2 BETWEEN :startDate AND :endDate
         )
   )
   multiStatePollutantData 
   JOIN
      (
         SELECT
            site_code,
            state_name 
         FROM
            vdhavaleswarapu.state s 
            JOIN
               vdhavaleswarapu.site si 
               ON s.state_code = si.state_code
      )
      siteData 
      ON siteData.site_code = multiStatePollutantData.site_code 
GROUP BY
   to_char(multiStatePollutantData.date_str2, 'MM'),
   extract(year 
from
   multiStatePollutantData.date_str2),
   siteData.state_name 
ORDER BY
   siteData.state_name,
   year,
   month;
