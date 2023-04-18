package com.joGram.whenisit.dates;



import jakarta.validation.constraints.NotNull;

import java.time.ZoneId;

public class TransferDate {


    @NotNull
    private final String currentDateString;
    @NotNull
    private final String toTimeZone;
    @NotNull
    private final String fromTimeZone;

    @NotNull
    private final String fromUTCOffset;



    public TransferDate(String currentDateString, String toTimeZone,String fromTimeZone,String fromUTCOffset){
        this.currentDateString = currentDateString;
        this.toTimeZone = toTimeZone;
        this.fromTimeZone = fromTimeZone;
        this.fromUTCOffset = fromUTCOffset;
    }

    public String getTimeZone(){
        return this.toTimeZone;
    }
    public String getDate(){
        return this.currentDateString;
    }

    public String getFromTimeZone() {
        return this.fromTimeZone;
    }

    public long getFromOffset() {
        return Long.parseLong(fromUTCOffset);
    }
}
