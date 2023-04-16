package com.joGram.whenisit.dates;



import jakarta.validation.constraints.NotNull;

public class TransferDate {


    @NotNull
    private final String currentDateString;
    @NotNull
    private final String timeZone;

    public TransferDate(String currentDateString, String timeZone){
        this.currentDateString = currentDateString;
        this.timeZone = timeZone;
    }

    public String getTimeZone(){
        return this.timeZone;
    }
    public String getDate(){
        return this.currentDateString;
    }

}
