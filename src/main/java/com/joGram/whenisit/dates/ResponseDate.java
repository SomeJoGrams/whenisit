package com.joGram.whenisit.dates;

import java.util.Date;
import java.util.TimeZone;

public class ResponseDate {

    private String dateString;
    private String inputTimeZone;
    private String fromTimeZone;


    public ResponseDate(String dateString,String timeZone,String fromTimeZone){
        this.dateString = dateString;
        this.inputTimeZone = timeZone;
        this.fromTimeZone = fromTimeZone;
    }


    public String getDate(){
        return dateString;
    }

    public String getToTimeZone(){
        return inputTimeZone;
    }

    public String getFromTimeZone(){
        return fromTimeZone;
    }

//    public int getUTCOffsetHours(){ // TODO use zone id
//        return inputTimeZone.getRawOffset() / 1000 / 60 / 60 ;
//    }

}
