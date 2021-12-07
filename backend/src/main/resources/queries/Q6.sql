SELECT
    ROWNUM AS RANK,
    STATE,
    AQI AS AVGAQI
FROM
    (
        SELECT
            TO_CHAR(AQIDATA.DATE_STR2, 'YYYY') AS YEAR,
            SITESTATE.STATE_NAME AS STATE,
            AVG(AQIDATA.AQI) AS AQI
        FROM
            (
                SELECT
                    AQI,
                    DATE_STR2,
                    SITE_CODE
                FROM
                    VDHAVALESWARAPU.OBSERVATION O
                WHERE
                    (
                            O.SITE_CODE IN
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
                            )
                        )
            )
                AQIDATA
                JOIN
            (
                SELECT
                    SITE_CODE,
                    STATE_NAME
                FROM
                    VDHAVALESWARAPU.STATE S
                        JOIN
                    VDHAVALESWARAPU.SITE SI
                    ON S.STATE_CODE = SI.STATE_CODE
            )
                SITESTATE
            ON SITESTATE.SITE_CODE = AQIDATA.SITE_CODE
        WHERE
                TO_CHAR(AQIDATA.DATE_STR2, 'YYYY') = '2020'
        GROUP BY
            SITESTATE.STATE_NAME,
            TO_CHAR(AQIDATA.DATE_STR2, 'YYYY')
        ORDER BY
            AQI
    );