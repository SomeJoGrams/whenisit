package com.joGram.whenisit.dates;



import jakarta.validation.constraints.NotNull;

public class TransferDate {


    @NotNull
    private final String currentDateString;
    @NotNull
    private final String toTimeZone;

    public TransferDate(String currentDateString, String toTimeZone){
        this.currentDateString = currentDateString;
        this.toTimeZone = toTimeZone;
    }

    public String getTimeZone(){
        return this.toTimeZone;
    }
    public String getDate(){
        return this.currentDateString;
    }

}
