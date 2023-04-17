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
            return null;
        }
        else{
            Date inputDate;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//(int)DateFormat.LONG);//,DateCalculator.resolveLocale(date.getTimeZone()));

            TimeZone timeZone = TimeZone.getTimeZone(transferDate.getTimeZone());//TimeZone.getTimeZone(zoneId);
            TimeZone.setDefault(timeZone);

            Calendar calendar = Calendar.getInstance(timeZone);// default utc instance

            dateFormat.setCalendar(calendar);

            try{
                inputDate = dateFormat.parse(transferDate.getDate());
            }
            catch (ParseException e){
                System.out.println("couldnt parse the date");
                return null;
            }
//            ZoneId zoneId= ZoneId.of("UTC"); // change the timezone
//            TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");//TimeZone.getTimeZone(zoneId);

            calendar.setTimeInMillis(inputDate.getTime());
            calendar.setTimeZone(timeZone);

            Date changedDate = calendar.getTime();
            String displayName = timeZone.toZoneId().getDisplayName(TextStyle.FULL,Locale.getDefault());

            String resultDateString = dateFormat.format(changedDate);


            System.out.println(displayName);
            System.out.println(resultDateString);
            ResponseDate responseDate = new ResponseDate(resultDateString,calendar.getTimeZone());
            return responseDate;
        }

    }

}
