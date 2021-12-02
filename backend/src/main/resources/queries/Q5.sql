  (SELECT MONTH,
          YEAR,
          count(avgAQI) AS DAYCOUNT
   FROM
     (SELECT dateData.year,
             dateData.date_id,
             dateData.month,
             avg(aqiData.aqi) avgAQI
      FROM
        (SELECT date_id,
                aqi
         FROM VDHAVALESWARAPU.observation o
         WHERE (o.site_code in
                  (SELECT *
                   FROM
                     (SELECT site_code
                      FROM VDHAVALESWARAPU.State state
                      JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code
                      JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code
                      WHERE state_name = :state)))) aqiData,

        (SELECT dc.date_id,
                dc.year,
                dc.month
         FROM VDHAVALESWARAPU.datecollected dc
         WHERE TO_DATE(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY') BETWEEN TO_DATE(:startDate, 'MM/DD/YYYY') AND TO_DATE(:endDate, 'MM/DD/YYYY')) dateData
      WHERE aqiData.date_id = dateData.date_id
        AND aqiData.aqi != 0
      GROUP BY dateData.year,
               dateData.month,
               dateData.date_id)
   WHERE avgAQI >= :threshold
   GROUP BY MONTH,
            YEAR)
UNION
  (SELECT MONTH,
          YEAR,
          0
   FROM
     (SELECT dateData.year,
             dateData.date_id,
             dateData.month,
             avg(aqiData.aqi) avgAQI
      FROM
        (SELECT date_id,
                aqi
         FROM VDHAVALESWARAPU.observation o
         WHERE (o.site_code in
                  (SELECT *
                   FROM
                     (SELECT site_code
                      FROM VDHAVALESWARAPU.State state
                      JOIN VDHAVALESWARAPU.County county ON county.state_code = state.state_code
                      JOIN VDHAVALESWARAPU.Site site ON site.county_code = county.county_code
                      WHERE state_name = :state)))) aqiData,

        (SELECT dc.date_id,
                dc.year,
                dc.month
         FROM VDHAVALESWARAPU.datecollected dc
         WHERE TO_DATE(dc.month || '/' || dc.day || '/' || dc.year, 'MM/DD/YYYY') BETWEEN TO_DATE(:startDate, 'MM/DD/YYYY') AND TO_DATE(:endDate, 'MM/DD/YYYY')) dateData
      WHERE aqiData.date_id = dateData.date_id
        AND aqiData.aqi != 0
      GROUP BY dateData.year,
               dateData.month,
               dateData.date_id)
   GROUP BY MONTH,
            YEAR
   HAVING max(avgAQI) < :threshold)