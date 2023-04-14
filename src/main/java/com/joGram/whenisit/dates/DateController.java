package com.joGram.whenisit.dates;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.joGram.whenisit.dates.DateCalculator.resolveLocale;

@RestController
public class DateController {

//    @GetMapping("/")
//    public void serveIndex(){
//
//    }
//    @CrossOrigin(origins = "http://localhost:8080")
//    @GetMapping("/date")
//    public Date retDate(){
//        return new Date();
//    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/date")
    public TransferDate calculatedDate(@Valid @ModelAttribute("TransferDate") TransferDate date, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("found errors in date");
            return null;
        }
        else{
            Date inputDate;
            DateFormat dateFormat = DateFormat.getDateTimeInstance();//(int)DateFormat.LONG);//,DateCalculator.resolveLocale(date.getTimeZone()));
//            try{
//                inputDate = dateFormat.parse(date.getDate());
//            }
//            catch (ParseException e){
//                System.out.println("couldnt parse the date");
//                return null;
//            }

            Calendar calendar = Calendar.getInstance();
            inputDate = Calendar.getInstance().getTime();
            calendar.setTimeInMillis(inputDate.getTime());
            calendar.setTimeZone(TimeZone.getDefault());
            Date resultDate = calendar.getTime();
            StringBuffer stringBuffer =new StringBuffer();
            stringBuffer.append(calendar.getFirstDayOfWeek());
            TransferDate transferedDate = new TransferDate(stringBuffer.toString(),calendar.getTimeZone().getID());
            return transferedDate;
        }

    }

}
