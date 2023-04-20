package com.joGram.whenisit.dates;



import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.ZoneId;

public class TransferDate {


    @NotNull
    private final String currentDateString;
    @NotNull
    @Size(min=3, max=32)
    private final String toTimeZone;
    @NotNull
    @Size(min=3, max=32)
    private final String fromTimeZone;

    @NotNull
    @Digits(integer=10,fraction=0)
    private final long fromUTCOffset;



    public TransferDate(String currentDateString, String toTimeZone,String fromTimeZone,long fromUTCOffset){
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
        return fromUTCOffset;
    }
}
