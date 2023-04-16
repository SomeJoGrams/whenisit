package com.joGram.whenisit.dates;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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
            try{
                inputDate = dateFormat.parse(transferDate.getDate());
            }
            catch (ParseException e){
                System.out.println("couldnt parse the date");
                return null;
            }

            Calendar calendar = Calendar.getInstance();
            inputDate = calendar.getTime();
            calendar.setTimeInMillis(inputDate.getTime());
            calendar.setTimeZone(TimeZone.getDefault());

            Date changedDate = calendar.getTime();
            String resultDateString = dateFormat.format(changedDate);

            ResponseDate responseDate = new ResponseDate(resultDateString,calendar.getTimeZone());
            return responseDate;
        }

    }

}
