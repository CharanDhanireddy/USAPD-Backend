drop view getStateSites1;
Create view getStateSites1 AS
Select site_code
from VDHAVALESWARAPU.State state
         join VDHAVALESWARAPU.County county on county.state_code = state.state_code
         join VDHAVALESWARAPU.Site site on site.county_code = county.county_code
where state_name = 'Florida';
Select * from getStateSites1;

DROP VIEW allPollObs;

Create view allPollObs AS
Select pol.pollutant_name AS pollutant, round(avg(o.arithmetic_mean),3) as mean, dc.year
from VDHAVALESWARAPU.observation o
         natural join VDHAVALESWARAPU.datecollected dc
         natural join VDHAVALESWARAPU.pollutant pol
where (
                  pol.pollutant_name = 'NO2'
              and (
                          o.site_code in (Select site_code
                                          from getStateSites1)
                      )
          )
group by pol.pollutant_name, year
        order by year;

select * from allpollobs;

drop view pollutantChange;

create view pollutantChange AS
select
    round(((select mean from allpollobs where year=2017) / (select mean from allpollobs where year=2016)-1)*100,3) as change2017,
    round(((select mean from allpollobs where year=2018) / (select mean from allpollobs where year=2017)-1)*100,3) as change2018,
    round(((select mean from allpollobs where year=2019) / (select mean from allpollobs where year=2018)-1)*100,3) as change2019,
    round(((select mean from allpollobs where year=2020) / (select mean from allpollobs where year=2019)-1)*100,3) as change2020
from dual;

select * from pollutantChange;


create view populationVSpollution as
select *
from polluantChange p
         (select * from populationgrowth where state_code = (
    select state_code from state where state_name = 'Florida'
))


drop view populationgrowth;

create view populationgrowth as
select state_code, ((pop_2017-pop_2016)/pop_2016 * 100) as change2017, ((pop_2018-pop_2017)/pop_2017 * 100) as change2018, ((pop_2019-pop_2018)/pop_2018 * 100) as change2019, ((pop_2020-pop_2019)/pop_2019 * 100) as change2020
from population;

select * from populationgrowth;