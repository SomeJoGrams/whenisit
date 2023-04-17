package com.joGram.whenisit.dates;



import jakarta.validation.constraints.NotNull;

import java.time.ZoneId;

public class TransferDate {


    @NotNull
    private final String currentDateString;
    @NotNull
    private final String toTimeZone;
    @NotNull
    private final String fromTimeZoneOffset;



    public TransferDate(String currentDateString, String toTimeZone,String fromTimeZoneOffset){
        this.currentDateString = currentDateString;
        this.toTimeZone = toTimeZone;
        this.fromTimeZoneOffset = fromTimeZoneOffset;
    }

    public String getTimeZone(){
        return this.toTimeZone;
    }
    public String getDate(){
        return this.currentDateString;
    }

//    public String getFromTimeZone() {
//        return this.fromTimeZone;
//    }

    public int getFromOffset() {
        return Integer.parseInt(fromTimeZoneOffset) * 60 * 1000;
    }
}
