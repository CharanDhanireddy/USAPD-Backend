WITH POLLUTANT_DATA AS (
    SELECT
        : STATE AS STATE_POL,
        A.POLLUTANT,
        B.YEAR AS BIGYEAR,
        A.YEAR AS SMALLYEAR,
        ROUND(
                    (
                            (B.AVGPOLLUTANT - A.AVGPOLLUTANT)* 100
                        ) / (A.AVGPOLLUTANT),
                    5
            ) AS PERCENTAGE_DIFF
    FROM
        (
            SELECT
                POL.POLLUTANT_NAME AS POLLUTANT,
                ROUND(
                        AVG(O.ARITHMETIC_MEAN),
                        6
                    ) AS AVGPOLLUTANT,
                TO_CHAR(O.DATE_STR2, 'YYYY') YEAR
            FROM
                VDHAVALESWARAPU.OBSERVATION O NATURAL
                                                  JOIN VDHAVALESWARAPU.POLLUTANT POL
            WHERE
                (
                    (
                            O.SITE_CODE IN (
                            SELECT
                                SITE_CODE
                            FROM
                                (
                                    SELECT
                                        SITE_CODE
                                    FROM
                                        VDHAVALESWARAPU.STATE STATE
                                            JOIN VDHAVALESWARAPU.COUNTY COUNTY ON COUNTY.STATE_CODE = STATE.STATE_CODE
                                            JOIN VDHAVALESWARAPU.SITE SITE ON SITE.COUNTY_CODE = COUNTY.COUNTY_CODE
                                    WHERE
                                            STATE_NAME = : STATE
                                )
                        )
                        )
                    )
            GROUP BY
                POL.POLLUTANT_NAME,
                TO_CHAR(O.DATE_STR2, 'YYYY')
            ORDER BY
                POL.POLLUTANT_NAME,
                TO_CHAR(O.DATE_STR2, 'YYYY')
        ) A,
        (
            SELECT
                POL.POLLUTANT_NAME AS POLLUTANT,
                ROUND(
                        AVG(O.ARITHMETIC_MEAN),
                        6
                    ) AS AVGPOLLUTANT,
                TO_CHAR(O.DATE_STR2, 'YYYY') YEAR
            FROM
                VDHAVALESWARAPU.OBSERVATION O NATURAL
                                                  JOIN VDHAVALESWARAPU.POLLUTANT POL
            WHERE
                (
                    (
                            O.SITE_CODE IN (
                            SELECT
                                SITE_CODE
                            FROM
                                (
                                    SELECT
                                        SITE_CODE
                                    FROM
                                        VDHAVALESWARAPU.STATE STATE
                                            JOIN VDHAVALESWARAPU.COUNTY COUNTY ON COUNTY.STATE_CODE = STATE.STATE_CODE
                                            JOIN VDHAVALESWARAPU.SITE SITE ON SITE.COUNTY_CODE = COUNTY.COUNTY_CODE
                                    WHERE
                                            STATE_NAME = : STATE
                                )
                        )
                        )
                    )
            GROUP BY
                POL.POLLUTANT_NAME,
                TO_CHAR(O.DATE_STR2, 'YYYY')
            ORDER BY
                POL.POLLUTANT_NAME,
                TO_CHAR(O.DATE_STR2, 'YYYY')
        ) B
    WHERE
                A.YEAR + 1 = B.YEAR
      AND A.POLLUTANT = B.POLLUTANT
)
SELECT
    A.STATE_NAME,
    B.YEAR,
    POLLUTANT_DATA.POLLUTANT,
    POLLUTANT_DATA.PERCENTAGE_DIFF AS POLLUTANT_PERCENTAGE_DIFF,
    ROUND(
                    (B.POPULATION - A.POPULATION)* 100 / (A.POPULATION),
                    5
        ) POPULATION_PERCENTAGE_DIFF
FROM
    VDHAVALESWARAPU.POPULATION_TOTAL A,
    VDHAVALESWARAPU.POPULATION_TOTAL B,
    POLLUTANT_DATA
WHERE
        A.YEAR = POLLUTANT_DATA.SMALLYEAR
  AND B.YEAR = POLLUTANT_DATA.BIGYEAR
  AND A.STATE_NAME = POLLUTANT_DATA.STATE_POL
  AND B.STATE_NAME = A.STATE_NAME;