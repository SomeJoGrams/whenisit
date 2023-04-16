package com.joGram.whenisit.dates;

import java.util.Date;
import java.util.TimeZone;

public class ResponseDate {

    private String dateString;
    private TimeZone inputTimeZone;


    public ResponseDate(String dateString,TimeZone timeZone){
        this.dateString = dateString;
        this.inputTimeZone = timeZone;
    }


    public String getDate(){
        return dateString;
    }

    public String getTimeZone(){
        return inputTimeZone.getID();
    }

    public int getUTCOffsetHours(){
        return inputTimeZone.getRawOffset() / 1000 / 60 / 60 ;
    }

}
