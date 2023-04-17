package com.joGram.whenisit.dates;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.spi.TimeZoneNameProvider;


@RestController
public class DateController {

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/date")
    public ResponseDate calculatedDate(@Valid @ModelAttribute("TransferDate") TransferDate transferDate, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("found errors in date");
//            result.getAllErrors().forEach(errorObj -> {
//                System.out.println(errorObj.toString());
//            });
            return null;
        }
        else{

            // parse the date with the from-time zone afterward calculate the translated time from the to Time zone
            Date inputDate;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//(int)DateFormat.LONG);//,DateCalculator.resolveLocale(date.getTimeZone()));
            // idea translate the date from the default js format utc to the from time zone to get the input date
            // then calculate the to time zone and return it


            TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
            utcTimeZone.setRawOffset(transferDate.getFromOffset());
            //            TimeZone fromTimeZone = TimeZone.getTimeZone(transferDate.getFromTimeZone());//TimeZone.getTimeZone(zoneId);
            TimeZone toTimeZone = TimeZone.getTimeZone(transferDate.getTimeZone());

            TimeZone.setDefault(utcTimeZone);
            Calendar calendar = Calendar.getInstance(utcTimeZone);// default utc instance
            dateFormat.setCalendar(calendar);

//            TimeZone.setDefault(fromTimeZone);
//            Calendar calendar = Calendar.getInstance(fromTimeZone);// default utc instance
//            dateFormat.setCalendar(calendar);

            try{
                inputDate = dateFormat.parse(transferDate.getDate());
            }
            catch (ParseException e){
                System.out.println("couldnt parse the date");
                return null;
            }

            calendar.setTimeInMillis(inputDate.getTime());
            calendar.setTimeZone(toTimeZone);
            TimeZone.setDefault(toTimeZone);


            Date changedDate = calendar.getTime();
            String displayName = toTimeZone.toZoneId().getDisplayName(TextStyle.FULL,Locale.getDefault());

            String resultDateString = dateFormat.format(changedDate);


            System.out.println(displayName);
            System.out.println(resultDateString);
            ResponseDate responseDate = new ResponseDate(resultDateString,calendar.getTimeZone());
            return responseDate;
        }

    }

}
