package com.joGram.whenisit.dates;

import java.util.Date;
import java.util.TimeZone;

public class ResponseDate {

    private String dateString;
    private String inputTimeZone;


    public ResponseDate(String dateString,String timeZone){
        this.dateString = dateString;
        this.inputTimeZone = timeZone;
    }


    public String getDate(){
        return dateString;
    }

    public String getTimeZone(){
        return inputTimeZone;
    }

//    public int getUTCOffsetHours(){ // TODO use zone id
//        return inputTimeZone.getRawOffset() / 1000 / 60 / 60 ;
//    }

}
