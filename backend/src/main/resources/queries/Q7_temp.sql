select a.pollutant, b.year, round(((b.avgPollutant - a.avgPollutant)*100)/(a.avgPollutant),5) as percentage_diff
from (
         Select pol.pollutant_name AS pollutant, round(avg(o.arithmetic_mean),6) as avgPollutant, to_char(o.date_str2, 'YYYY') year
         from VDHAVALESWARAPU.observation o
                  natural join VDHAVALESWARAPU.pollutant pol
         where (
                           pol.pollutant_name = :pollutant
                       and
                           (
                                   o.site_code in (Select site_code from
                                   (Select site_code from VDHAVALESWARAPU.State state
                                                              join VDHAVALESWARAPU.County county on county.state_code = state.state_code
                                                              join VDHAVALESWARAPU.Site site on site.county_code = county.county_code
                                    where state_name = :state)
                               )
                               )
                   )
         group by pol.pollutant_name, to_char(o.date_str2, 'YYYY')
         order by pol.pollutant_name, to_char(o.date_str2, 'YYYY')
     ) a,
     (
         Select pol.pollutant_name AS pollutant, round(avg(o.arithmetic_mean),6) as avgPollutant, to_char(o.date_str2, 'YYYY') year
         from VDHAVALESWARAPU.observation o
                  natural join VDHAVALESWARAPU.pollutant pol
         where (
                           pol.pollutant_name = :pollutant
                       and
                           (
                                   o.site_code in (Select site_code from
                                   (Select site_code from VDHAVALESWARAPU.State state
                                                              join VDHAVALESWARAPU.County county on county.state_code = state.state_code
                                                              join VDHAVALESWARAPU.Site site on site.county_code = county.county_code
                                    where state_name = :state)
                               )
                               )
                   )
         group by pol.pollutant_name, to_char(o.date_str2, 'YYYY')
         order by pol.pollutant_name, to_char(o.date_str2, 'YYYY')
     ) b
where a.year + 1 = b.year and a.pollutant = b.pollutant;