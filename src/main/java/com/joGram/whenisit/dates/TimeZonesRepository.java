package com.joGram.whenisit.dates;

import org.springframework.data.repository.CrudRepository;

public interface TimeZonesRepository extends CrudRepository<TimeZoneCity,Long>{
    TimeZoneCity findByCity(String city);

    TimeZoneCity findByTimeZone(String timeZone);



}
