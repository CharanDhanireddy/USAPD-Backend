SELECT
   YEAR,
   SEASON,
   ROUND(AVG(MEANVAL), 3) AS MEANVALUE 
FROM
   (
      SELECT
         TO_CHAR(AP.DATE_STR2, 'YYYY') AS YEAR,
         TO_CHAR(AP.DATE_STR2, 'WW') AS WEEK,
         AVG(AP.ARITHMETIC_MEAN) AS MEANVAL,
         CASE
            WHEN
               TO_CHAR(AP.DATE_STR2, 'WW') BETWEEN 11 AND 24 
            THEN
               'Spring' 
            WHEN
               TO_CHAR(AP.DATE_STR2, 'WW') BETWEEN 25 AND 37 
            THEN
               'Summer' 
            WHEN
               TO_CHAR(AP.DATE_STR2, 'WW') BETWEEN 38 AND 50 
            THEN
               'Fall' 
            WHEN
               TO_CHAR(AP.DATE_STR2, 'WW') >= 51 
               OR TO_CHAR(AP.DATE_STR2, 'WW') < 11 
            THEN
               'Winter' 
         END
         AS SEASON 
      FROM
         (
            SELECT
               ARITHMETIC_MEAN,
               DATE_STR2 
            FROM
               VDHAVALESWARAPU.OBSERVATION O 
            WHERE
               (
                  O.POLLUTANT_CODE = 
                  (
                     SELECT
                        POLLUTANT_CODE 
                     FROM
                        VDHAVALESWARAPU.POLLUTANT P 
                     WHERE
                        (
                           P.POLLUTANT_NAME = :pollutant 
                        )
                  )
               )
               AND 
               (
                  O.SITE_CODE IN 
                  (
                     SELECT
                        SITE_CODE 
                     FROM
                        (
                           SELECT
                              SITE_CODE 
                           FROM
                              VDHAVALESWARAPU.STATE STATE 
                              JOIN
                                 VDHAVALESWARAPU.COUNTY COUNTY 
                                 ON COUNTY.STATE_CODE = STATE.STATE_CODE 
                              JOIN
                                 VDHAVALESWARAPU.SITE SITE 
                                 ON SITE.COUNTY_CODE = COUNTY.COUNTY_CODE 
                           WHERE
                              STATE_NAME = :state 
                        )
                  )
               )
               AND 
               (
                  O.DATE_STR2 BETWEEN :startDate AND :endDate 
               )
         )
         AP 
      GROUP BY
         TO_CHAR(AP.DATE_STR2, 'YYYY'),
         TO_CHAR(AP.DATE_STR2, 'WW') 
      ORDER BY
         YEAR 
   )
GROUP BY
   SEASON,
   YEAR 
ORDER BY
   YEAR,
   CASE
      WHEN
         season = 'Spring' 
      THEN
         '1' 
      WHEN
         season = 'Summer' 
      THEN
         '2' 
      WHEN
         season = 'Fall' 
      THEN
         '3' 
      WHEN
         season = 'Winter' 
      THEN
         '4' 
   END
;

