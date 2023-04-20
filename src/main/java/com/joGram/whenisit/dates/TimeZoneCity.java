package com.joGram.whenisit.dates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TimeZoneCity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String city;
    private String timeZone;

    // only used by JPA
    protected TimeZoneCity(){

    }
    public TimeZoneCity(String city,String timeZone){
        this.timeZone = timeZone;
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCity() {
        return city;
    }

    public String getPair(){
        StringBuilder sB = new StringBuilder();
        sB.append(city);
        sB.append(", ");
        sB.append(timeZone);
        return sB.toString();
    }
}
